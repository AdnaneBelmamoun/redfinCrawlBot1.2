package redfinCrawlers;

import java.util.Vector;

public class RedfinSearchUrlMatchs {
	private String Keyword = "";
	private Vector<RedfinSearchExtraMatch>  allextramatchs ;
	private RedfinSearchMatch exactMatch;
	private String resultCode ="";
	private String errorMessage ="";
	private String version ="";
	
	public RedfinSearchUrlMatchs(String Key, Vector<RedfinSearchExtraMatch> AllExtra, RedfinSearchMatch Exact, Vector<String> inf){
		this.Keyword = Key;
		this.allextramatchs = AllExtra;
		this.exactMatch=Exact;
		this.resultCode = inf.get(0);
		this.errorMessage =inf.get(1);
		this.version =inf.get(2);
		
	}
	

	@SuppressWarnings("unused")
	private Vector<RedfinSearchExtraMatch> getExtraMatches_Bysection(final String section_name){
		Vector<RedfinSearchExtraMatch> res = new Vector<RedfinSearchExtraMatch>();
		Vector<RedfinSearchExtraMatch> allmatches = this.getAllextramatchs();
		for(RedfinSearchExtraMatch extramatch: allmatches){
			if(extramatch.getExtraMatchSectionName().contentEquals(section_name)){
				res.add(extramatch);
			}
		}
		return res;
	}
	public String getKeyword() {	return Keyword;	}
	public void setKeyword(String keyword) {	Keyword = keyword;	}
	
	public Vector<RedfinSearchExtraMatch> getAllextramatchs() {	return allextramatchs;	}
	public void setAllextramatchs(Vector<RedfinSearchExtraMatch> allextramatchs) {	this.allextramatchs = allextramatchs;	}
	
	public RedfinSearchMatch getExactMatch() {		return exactMatch;	}
	public void setExactMatch(RedfinSearchMatch exactMatch) {		this.exactMatch = exactMatch;	}
	
	public String getResultCode() {	return resultCode;	}	
	public void setResultCode(String resultCode) {	this.resultCode = resultCode;	}
	
	public String getErrorMessage() {	return errorMessage;	}
	public void setErrorMessage(String errorMessage) {	this.errorMessage = errorMessage;	}
	
	public String getVersion() {	return version;	}
	public void setVersion(String version) { this.version = version;}

	public static void main(String[] args) {
		

	}

}
