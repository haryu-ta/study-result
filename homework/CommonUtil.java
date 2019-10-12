package oracle;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CommonUtil {

	public static HashMap<String, String> readConfig(String confpath) throws IOException {

		HashMap<String,String> map = new HashMap<String,String>();

		ResourceBundle rb = ResourceBundle.getBundle(confpath);
		Enumeration<String> em = rb.getKeys();
		String key;
		while ( em.hasMoreElements() ){
			key=em.nextElement();
			map.put(key,rb.getString(key));
		}


		return map;
	}

}
