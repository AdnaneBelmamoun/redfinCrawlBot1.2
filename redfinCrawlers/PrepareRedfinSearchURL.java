package redfinCrawlers;

public class PrepareRedfinSearchURL {
	
	private String Searchkeyword  ="downey";
	private String SearchMatcheFactoryURl;
	private String [] SearchMatcheFactoryURl_baseinfos ={"https://www.redfin.com","/stingray/do/location-autocomplete?location=","&start=0&count=10&v=2&al=1"};
	private String Brut_Str_SearchResult_URLinfo ;
	@SuppressWarnings("unused")
	private RedfinSearchUrlMatchs  searchMatchs ;

	public PrepareRedfinSearchURL(String key, String [] Factory_UrlBase){
		this.Searchkeyword = key;
		this.SearchMatcheFactoryURl_baseinfos = Factory_UrlBase;
		this.SearchMatcheFactoryURl = Factory_UrlBase[0]+Factory_UrlBase[1]+key+Factory_UrlBase[2];
		
		System.out.println("Searchkeyword :   "+this.Searchkeyword);
		System.out.println("SearchMatcheFactoryURl_baseinfos :  "+this.SearchMatcheFactoryURl_baseinfos);
		System.out.println("SearchMatcheFactoryURl   :  "+this.SearchMatcheFactoryURl);
		// ici get searchMatchs method doit rendre un objet RedfinSearchUrlMatchs contenant tout les objets de recherche 
		//    parseSearchMathData();
	}
	
	/*private RedfinSearchUrlMatchs  parseSearchMatchData(String key, String [] Factory_UrlBase){
		RedfinSearchUrlMatchs res = new RedfinSearchUrlMatchs(key, Vector<RedfinSearchExtraMatch> AllExtra, RedfinSearchMatch Exact, Vector<String> inf);
	return res;
	}*/
	public String formattingFactoryURL(){
		String res="";
		String str1= this.SearchMatcheFactoryURl_baseinfos[0];
		String str2=this.SearchMatcheFactoryURl_baseinfos[1];
		String str3=this.SearchMatcheFactoryURl_baseinfos[2];
				res = str1+str2+this.Searchkeyword+str3;
	return res;}
	
 
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public String getSearchkeyword() {		return Searchkeyword;	}
	public void setSearchkeyword(String searchkeyword) {	Searchkeyword = searchkeyword;	}

	public String getSearchMatcheFactoryURl() {		return SearchMatcheFactoryURl;	}
	public void setSearchMatcheFactoryURl(String searchMatcheFactoryURl) {	SearchMatcheFactoryURl = searchMatcheFactoryURl;	}

	public String [] getSearchMatcheFactoryURl_baseinfos() {	return SearchMatcheFactoryURl_baseinfos;	}
    public void setSearchMatcheFactoryURl_baseinfos(String [] searchMatcheFactoryURl_baseinfos) {SearchMatcheFactoryURl_baseinfos = searchMatcheFactoryURl_baseinfos;}


	public String getBrut_Str_SearchResult_URLinfo() {	return Brut_Str_SearchResult_URLinfo; }
	public void setBrut_Str_SearchResult_URLinfo(String brut_Str_SearchResult_URLinfo) {Brut_Str_SearchResult_URLinfo = brut_Str_SearchResult_URLinfo;	}

}
