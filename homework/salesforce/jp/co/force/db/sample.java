package jp.co.force.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.PrefecturesInfo__c;
import com.sforce.soap.enterprise.sobject.SObject;

public class sample {

	private static String SFDC_USER_ID = "ita@mail.com";
	private static String SFDC_PASSWORD  = "enoch20110626";
	private static String SFDC_END_POINT = "https://login.salesforce.com/services/Soap/c/21.0/0DF10000000TSF7";

	public static void main(String[] args) {

		SfdcSession sfdcSession = null;

		try{

		    System.setProperty("http.proxyHost", "tisproxy.intra.tis.co.jp");
		    System.setProperty("http.proxyPort", "8080");

		    // セールスフォースへのセッションを作成
		    sfdcSession = SfdcSession.openSession(
		        SFDC_USER_ID,
		        SFDC_PASSWORD,
		        SFDC_END_POINT);

		    // タイムアウト時間設定
		    sfdcSession.setTimeOut(600000);

		    //レコードの作成
		    PrefecturesInfo__c  pref = new PrefecturesInfo__c();
		    pref.setName("三重");
		    pref.setPrefecturalCapital__c("津");
		    pref.setPrefKbn__c("県");
		    Calendar cal = Calendar.getInstance();
		    cal.set(2015, 5, 1, 7, 0, 0);
		    pref.setCreatedDate(cal);
		    sfdcSession.binding.create(new PrefecturesInfo__c[]{ pref});


		    List<PrefecturesInfo__c> objectList = new ArrayList<PrefecturesInfo__c>();
		    // SOQLの作成
		    StringBuffer selectQuery = new StringBuffer();
		    selectQuery.append("SELECT ");
		    selectQuery.append("Id, ");
		    selectQuery.append("Name, ");
		    selectQuery.append("prefKbn__c, ");
		    selectQuery.append("prefecturalCapital__c, ");
		    selectQuery.append("CreatedDate ");
		    selectQuery.append("FROM ");
		    selectQuery.append("PrefecturesInfo__c ");

		    // クエリ実行
		    QueryResult rs = sfdcSession.binding.query(selectQuery.toString());

		    // 検索結果の取得
		    boolean done = false;
		    if (rs.getSize() > 0){
		        while (!done) {
		            for (SObject so : rs.getRecords()) {
		                objectList.add((PrefecturesInfo__c)so);
		            }
		            // 取得件数が多い場合は再検索をおこなう
		            if (rs.isDone()) {
		                done = true;
		            } else {
		                rs = sfdcSession.binding.queryMore(rs.getQueryLocator());
		            }
		        }
		    }

		    File csv = new File("writers.csv"); // CSVデータファイル

		    // 追記モード
		    BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));

		    Iterator<PrefecturesInfo__c> iter = objectList.iterator();
		    while(iter.hasNext()) {
		    	PrefecturesInfo__c _sb = iter.next();
		        bw.write( "\"" + _sb.getId() + "\",\"" + _sb.getName() + "\",\"" + "\"" +  _sb.getCreatedDate());
		        bw.newLine();
		    }
		    bw.close();

		}catch(Exception e){
		    e.printStackTrace();
		}

	}

}
