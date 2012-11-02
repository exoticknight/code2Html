package core;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Generator {
	private Schema.Source schema; // schema of the source file
	private String fontColor; // color of the keyword
	private String fontSize; // font-size of the code
	private String sourcePath; // path of the source file
	private String outputPath; // path of the output file
	private String sourceName;
	private String outputName;
	private String code; // contains the code
	private int lineCount; // number of lines
	private boolean ready = false;
	private Pattern patternKeyword;
	private Pattern patternSingleComment;
	private Pattern patternMutiComment;
	private Formater formaterKeyword = Formater.KEYWORD;
	private Formater formaterSingleComment = Formater.SINGLECOMMENT;
	private Formater formaterMutiComment = Formater.MUTICOMMENT;
	
	public Generator(Schema.Source schema, String fontColor, String fontSize, String sourcePath, String outputPath, String sourceName, String outputName) {
		this.schema = schema;
		this.fontColor = fontColor;
		this.fontSize = fontSize;
		this.sourcePath = sourcePath;
		this.outputPath = outputPath;
		this.sourceName = sourceName;
		this.outputName = outputName;
		this.lineCount = 0;
	}

	public void getready() {
		if(sourcePath == null || sourceName == null || outputName == null) {
			return;
		}
		/* genarate the pattern */
		try {
			patternKeyword = Pattern.compile("(" + schema.getSingleComment() + "|" + schema.getMutiComment() + ")|(" + schema.getKeyword() + ")");
			patternSingleComment = Pattern.compile("(" + schema.getMutiComment() + ")|(" + schema.getSingleComment() + ")");
			patternMutiComment = Pattern.compile("(" + schema.getSingleComment() + ")|(" + schema.getMutiComment() + ")");
			ready = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean transform() {
		// the generator must get ready
		if(!ready) return false;
		// read in the code
		SourceFile source = new SourceFile(this.sourcePath);
		// !!must invoke getCode() before invoke getLineCount()
		code = source.getCode();
		lineCount = source.getLineCount();
		//System.out.println(code);
		if(code.isEmpty()) return false;
		// replace the match string
		// still needs to being improved
		code = escapeHTML(code);
		formatCode(patternKeyword, formaterKeyword);
		formatCode(patternSingleComment, formaterSingleComment);
		formatCode(patternMutiComment, formaterMutiComment);
		//code = escapeHTMLCode(code);
		// generate output file
		TerminalFile output = new TerminalFile(outputPath, outputName);
		output.writeCode(sourceName, fontColor, fontSize, code, lineCount);
		return true;
	}
	
	private void formatCode(Pattern regex, Formater formater) {
		Matcher matcher = regex.matcher(code);
		StringBuffer sb = new StringBuffer();
		String targetCode;
		while(matcher.find()) {
			if(matcher.start(1)>-1) {
				// do nothing
			} else if(matcher.start(2)>-1) {
				targetCode = matcher.group(2);
				System.out.println(targetCode);
				matcher.appendReplacement(sb, formater.getPrefix() + targetCode + formater.getSuffix());
			}
		}
		matcher.appendTail(sb);
		code = sb.toString();
	}
	
	private String escapeHTML(String str) {
		 return str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
	
	private String escapeHTMLCode(String str) {
		return str.replaceAll("\\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	}
	
}
