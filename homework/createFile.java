package oracle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;


public class createFile {

	private static final String ENTER = "\r\n";
	private static final String propertiesname = "oracle.setting";

	public static void csv(String tablename) throws Exception{

		HashMap<String,String> map = CommonUtil.readConfig(propertiesname);

		Connection con = null;

		StringBuilder sb = new StringBuilder();
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		//ドライバからの実行の場合
		if ( ste.getMethodName().equals("csv") ){

			con = DriverManager.getConnection("jdbc:default:connection:");

		}else{

			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection (
	                "jdbc:oracle:thin:@" + map.get("SERVER") + ":" + map.get("PORT") + ":" + map.get("SID"),
	                map.get("DBUSER"),
	                map.get("PASSWORD"));

		}

		Statement state  = con.createStatement();

		String sql = "select * from " + tablename;
		ResultSet rs = state.executeQuery(sql);

		StringBuilder csv = new StringBuilder();

		//ヘッダ行組立
		for (int colno = 1 ; colno <= rs.getMetaData().getColumnCount() ; colno++ ){

			csv.append(rs.getMetaData().getColumnName(colno));
			if( colno != rs.getMetaData().getColumnCount() ) csv.append(",");

		}

		while ( rs.next() ){

			csv.append(ENTER);

			for (int colno = 1 ; colno <= rs.getMetaData().getColumnCount() ; colno++ ){

				if ( rs.getMetaData().getColumnTypeName(colno).equals("NUMBER") ){

					csv.append(rs.getBigDecimal(colno) == null ? "" : rs.getBigDecimal(colno).toString());

				}else if( rs.getMetaData().getColumnTypeName(colno).contains("CHAR") || rs.getMetaData().getColumnTypeName(colno).contains("VARCHAR2") ){

					csv.append(rs.getString(colno) == null ? "" : rs.getString(colno) );

				}else if( rs.getMetaData().getColumnTypeName(colno).equals("DATE") ){

					csv.append(rs.getDate(colno) == null ? "" : rs.getDate(colno).toString().replaceAll("-", "/"));

				}else if ( rs.getMetaData().getColumnTypeName(colno).equals("TIMESTAMP") ){

					csv.append(rs.getTimestamp(colno) == null ? "" : rs.getTimestamp(colno).toString().replaceAll("-","/"));

				}

				if( colno != rs.getMetaData().getColumnCount() ) csv.append(",");

			}

		}

		rs.close();
		state.close();
		con.close();

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(map.get("OUTPUTDIR") + File.separator + tablename + ".csv"),"UTF-8"));
		out.write(csv.toString());
		out.close();

	}

}
