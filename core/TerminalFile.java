package core;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class TerminalFile {
	private String outputpath;
	private String outputName;

	public TerminalFile(String outputpath, String outputName) {
		this.outputpath = outputpath;
		this.outputName = outputName;
	}
	
	// ganerate the html code
	public void writeCode(String sourceName, String fontColor, String fontSize, String code, int lineCount) {
		StringBuffer lineCountCode = new StringBuffer(1024);
		// genarate the line number DIVs
		for(int i=1;i<=lineCount;++i) {
			lineCountCode = lineCountCode.append(Schema.lineDIVPrefix + i + Schema.LineDIVsuffix);
		}
		String HTML = Schema.HTML_title + sourceName + Schema.title_HTML_size + fontSize + Schema.size_HTML_color + fontColor + Schema.color_HTML_code + code + Schema.code_HTML_line + lineCountCode + Schema.line_HTML;
		try {
			writeHTML(HTML);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	// code from stackoverflow
	// http://stackoverflow.com/questions/1001540/how-to-write-a-utf-8-file-with-java
	private void writeHTML(String html) throws IOException{
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputpath + outputName), "UTF-8"));
		try {
		    out.write(html);
		} finally {
		    out.close();
		}
	}
}
