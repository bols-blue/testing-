package jp.co.overtone.json2topCpp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NSLElement {

	String name;
	int width;
	int type;

	public NSLElement(String name) {
		this.init(name, 0, 0);
	}

	public NSLElement(String name2, int i, int j) {
		this.init(name2, i, j);
	}

	protected void init(String name, int i, int j) {
		this.setName(name);
		this.setType(i);
		this.setWidth(j);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
        Pattern pattern = Pattern.compile("\\[.+\\]");
        Matcher matcher = pattern.matcher(name);
        String strResult = matcher.replaceFirst("");

        Pattern argPattern = Pattern.compile("\\(.*\\)");
        Matcher argMatcher = argPattern.matcher(strResult);
        strResult = argMatcher.replaceFirst("");
        this.name = strResult;
	}

	public int getType() {
		return type;
	}

	public void setType(int i) {
		this.type = i;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public String toString() {
		return "name:\""+name+"\""+" type:\""+type+"\""+" width:\""+width+"\" "+this.getClass().getName();
	}

	public void setWidth(int j) {
		this.width = j;
	}
}
