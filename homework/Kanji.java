import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Kanji {

	private static String confpath = "kanji.properties";

	public static String convert(String kanji) throws IOException {

		 HashMap<String,String> name = readConfig();
		 String returnkanji = "";

		 if( name.containsKey(kanji) ){

			returnkanji = name.get(kanji);

		 }else{

			 returnkanji = kanji;

		 }

		return returnkanji;

	}

	private static HashMap<String, String> readConfig() throws IOException {

		HashMap<String,String> map = new HashMap<String,String>();

		BufferedReader f = new BufferedReader(new InputStreamReader(new FileInputStream(confpath),"Shift_JIS"));
		String val;
		while( (val = f.readLine()) != null ){

			map.put(val.split("=")[0], val.split("=")[1]);

		}

//		map.put("惠", "恵");
//		map.put("營", "営");
		return map;
	}

}
