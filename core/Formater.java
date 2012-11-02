package core;

public final class Formater {
	private String className;
	
	public final static Formater KEYWORD = new Formater("keyword");
	public final static Formater SINGLECOMMENT = new Formater("comment");
	public final static Formater MUTICOMMENT = new Formater("comment");
	
	public Formater(String className) {
		this.className = className;
	}
	
	public String getPrefix() {
		return "<label class=\"" + className + "\">";
	}
	
	public String getSuffix() {
		return "</label>";
	}
	
}
