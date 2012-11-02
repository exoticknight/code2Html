package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SourceFile {
	private static String path; // path of souce file
	private static int lineCount = 0;

	public SourceFile(String path) {
		SourceFile.path = path;
	}
	
	public String getCode() {
		String code = null;
		try {
			code = readFile(path);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		finally {
			return code;
		}
	}
	
	public int getLineCount() {
		return lineCount;
	}
	
	private static String readFile(String path) throws IOException {
		FileInputStream stream = new FileInputStream(new File(path));
		String line;
		StringBuffer code = new StringBuffer(1024);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			while((line = br.readLine()) != null) {
				code.append(line + "\n");
				lineCount++;
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}finally {
			stream.close();
			return code.toString();
		}
	}
}
