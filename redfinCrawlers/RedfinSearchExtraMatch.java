package redfinCrawlers;

import java.util.Vector;

public class RedfinSearchExtraMatch {
    private String keyword= ""; 
    private String ExtraMatchSectionName ="";
	private Vector<RedfinSearchMatch> extramatchs ;
	
	private RedfinSearchExtraMatch(String key,Vector<RedfinSearchMatch> extramtches, String sectionname){
		this.keyword = key;
		this.ExtraMatchSectionName = sectionname;
		this.extramatchs = extramtches;
	}
	
	
	public String getKeyword() {		return keyword;	}
	public void setKeyword(String keyword) {	this.keyword = keyword;	}
	public String getExtraMatchSectionName() {	return ExtraMatchSectionName;	}
	public void setExtraMatchSectionName(String extraMatchSectionName) {	ExtraMatchSectionName = extraMatchSectionName;	}
	public Vector<RedfinSearchMatch> getExtraMatchs() {	return extramatchs;	}
    public void setExtralMatchs(Vector<RedfinSearchMatch> match) {	this.extramatchs = match;	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
