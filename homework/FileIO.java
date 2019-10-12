import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class FileIO {

	private static String filepath = "C:/work/test.txt";

	public static void write(String value) throws IOException{

		BufferedWriter f1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath),"UTF-8"));
		f1.write(value);
		f1.close();

	}

}
