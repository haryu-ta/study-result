#!/bin/sh

#################################################################################
#  Script name  : 移行用シェル
#  Description  : テーブル退避
#  Parameters   : 逆戻し区分               (0or1) 1 ⇒ 逆戻し
#                  インデックスリネーム区分 (0or1) 1 ⇒ リネーム実施
################################################################################

WORKDIR=/apl/aIss_info/batch/tmp
LOGDIR=/apl/aIss_info/batch/shells/joblog
LISTFILE=${WORKDIR}/MOVETBL.lst
INDEXLIST=${WORKDIR}/MOVEINDEX.lst

INDEX_RENAME_KBN=0

export AJSJOBNAME=`date +%Y%m%d%H%M%S`


function renameIndex()
{

    sqlplus -S owneruser/ZaW26yJM << EOF > /dev/null  2>&1
set pagesize 0
set echo off
set linesize 32767
set trimspool on
set feedback off
set verify off
set termout off
spool ${INDEXLIST}
SELECT 'ALTER TALBE ${1} RENAME CONSTRAINT ' || INDEX_NAME || ' TO BK_' || INDEX_NAME || ';' FROM USER_INDEXES WHERE TABLE_NAME = '${1}';
spool off
exit
EOF

    return ${?}

}

if [ ${#} -eq 2 ] &&
   [ ${2} == "1" ];
then
   INDEX_RENAME_KBN=1
fi


cat ${LISTFILE} | grep -v "#" | grep -v -e '^\s*$' | while read line
do

    tblname=`echo ${line} | awk '{print $2}'`
    newtblname=`echo ${line} | awk '{print $3}'`

    #索引もリネームの場合
    if [ ${INDEX_RENAME_KBN} -eq 1 ];
    then

        #リネーム対象を取得
        renameIndex ${tblname}

    fi

    result=${?}

    #テーブルリネーム
     /apl/aIss_info/batch/func/func_exec_sql2.sh exchange_table.sql ${tblname} ${newtblname}

    result2=0 #${?}

    if [ ${result2} -ne 0 ]||
    [ ${result} -ne 0 ];
    then

        echo "=================================="
        echo "${tblname}のリネームに失敗しました"
        echo "詳細ログ : ${LOGDIR}/${AJSJOBNAME}.log"
        exit

    else

        echo "正常リネーム : ${tblname} ⇒ ${newtblname}"

    fi

done

