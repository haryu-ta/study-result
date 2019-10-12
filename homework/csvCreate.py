# -*- coding: utf-8 -*-

import sys

from PyQt5.QtWidgets import QApplication, QWidget,QLabel, QLineEdit, QPushButton,QFileDialog
from PyQt5.QtGui import  QPalette,QColor,QFont
import openpyxl
from openpyxl import Workbook,load_workbook
import codecs

class csvCreate(QWidget):

    def __init__(self):
        super(csvCreate,self).__init__()
        self.frameCreate()

    def frameCreate(self):
        self.setWindowTitle("元データ選択")
        self.resize(500,230)

        self.fileselect_but = QPushButton("ディレクトリ選択",self)
        self.label = QLabel("選択されたファイル",self)
        self.textline = QLineEdit("",self)
        self.filecreate_but = QPushButton("CSV作成",self)

        self.label.setGeometry(20,0,200,75)
        self.fileselect_but.setGeometry(380,55,100,50)
        self.textline.setGeometry(20,55,350,50)
        self.filecreate_but.setGeometry(100,150,300,50)

        self.fileselect_but.clicked.connect(self.fileselect)
        self.filecreate_but.clicked.connect(self.filecreate)
        self.show()

    def fileselect(self):
        filename = QFileDialog.getOpenFileName(caption='ファイル選択',directory="C:/temp",filter="Excel (*.xlsx)")
        self.textline.setText(filename[0])

    def filecreate(self):
        if self.textline.text() == '' :
            return

        csvitem = ''
        wb = load_workbook(self.textline.text())
        for sn in wb.get_sheet_names():
            if not(sn.startswith('Sheet')):
                ws = wb.get_sheet_by_name(sn)
                #行数分ループ
                for r in ws.rows:
                    #列数分ループ
                    for (ind,c) in enumerate(r):
                        csvitem = csvitem + '\"' + c.value + '\"'
                        if len(r) != ind + 1 :
                            csvitem = csvitem + ','

                    csvitem = csvitem + '\n'
                    #print(csvitem,end="")

                csvwrite(self.textline.text(),sn,csvitem)

def csvwrite(path,sname,val):
    import os.path
    f = codecs.open('C:/temp' + '/' + sname + '.csv' ,'w','shift_jis')
    f.write(val)
    f.close()

def main():
    app = QApplication(sys.argv)
    frame = csvCreate()
    sys.exit(app.exec_())

if __name__ == '__main__':
    main()
    print("終了")