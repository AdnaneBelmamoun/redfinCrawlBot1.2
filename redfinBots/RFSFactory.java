package redfinBots;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

//import redfinCrawlers.RedfinSearchUrlMatchs;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.NameValuePair;



public class RFSFactory {

	private String SearchKeyword ;
	private String[] FactoryUrlBase ={"https://www.redfin.com","/stingray/do/location-autocomplete?location=","&start=0&count=10&v=2&al=1"};
	private String FullFactoryUrl;
	private RFSUrlMatchs rfsum;
	
	
	public RFSFactory(String key,WebClient wctemp){
		this.SearchKeyword =key;
		this.FullFactoryUrl = FactoryUrlBase[0]+FactoryUrlBase[1]+key+FactoryUrlBase[2];
		this.setRfsum(parseSearchFactoryUrlData(key, getFactoryResponse(key, wctemp)));
	}

	public RFSFactory(String key, String[] base,WebClient wctemp){
		this.SearchKeyword =key;
		this.FactoryUrlBase = base;

		this.FullFactoryUrl = base[0]+base[1]+key+base[2];
		this.setRfsum(parseSearchFactoryUrlData(key, getFactoryResponse(key, wctemp)));
	}
	
	public String getFactoryResponse(String key,WebClient wctemp){
		String BrutSearchUrlres="";
		String factoryurl = getgeneratedFactoryURl(key);

        Page Pageresult =null;
        try {
			wctemp.getPage((String)this.FactoryUrlBase[0]);
			wctemp.waitForBackgroundJavaScript(100000);
			Pageresult = wctemp.getPage(factoryurl);
			BrutSearchUrlres = Pageresult.getWebResponse().getContentAsString();
					} catch (FailingHttpStatusCodeException e) { e.printStackTrace();
					} catch (MalformedURLException e) {e.printStackTrace();
					} catch (IOException e) {e.printStackTrace();}
        return BrutSearchUrlres;
	}
	
	public RFSUrlMatchs parseSearchFactoryUrlData(String key, String BrutSearchUrl){
		RFSUrlMatchs RFSUM_res = null;
		
	if(!BrutSearchUrl.contains("exactMatch\":null")){
		
		
		// parsing Brut STring Matches to RFSUrlMatchs object.
		String brut_res1 = BrutSearchUrl.replace("{}&&{", "");
		String brut_res2 = brut_res1.replace(":{", "::");

		
		String resultCode = brut_res1.split("\"resultCode\":")[1].split(",")[0];
		String errorMessage=brut_res1.split("\"errorMessage\":")[1].split(",")[0];
		String version=brut_res1.split("\"version\":")[1].split(",")[0];
		
		String payloadbrut= brut_res2.split("\"payload\"::")[1];

		
		String sectionsbrut= payloadbrut.split("\"sections\":\\[\\{")[1];
		
		String [] sectionsbruts_tab =  sectionsbrut.split("\\}\\]\\},\\{"); 
	
		String payload_exactmatch ="";
		
		if(payloadbrut.contains("exactMatch")){
			payload_exactmatch = payloadbrut.split("\"exactMatch\"::")[1].split("\"\\},\"")[0];
		}else{
			if(payloadbrut.contains("\"rows\"")){
				payload_exactmatch = payloadbrut.split("\"rows\":\\[\\{")[1].split("\\},\\{\"")[0]; 
				
			}
			
		}

	
		// Infos Vector
		Vector<String> rfsmatchurlInfo = new Vector<String>();
		rfsmatchurlInfo.add(resultCode); 
		rfsmatchurlInfo.add(errorMessage); 
		rfsmatchurlInfo.add(version);
	
		Vector<NameValuePair> exactmatchinfostmp = new Vector<NameValuePair>();
		String [] nameValuepaires1 =  payload_exactmatch.replace("\",\"","\";;\"").replace("\"\"","\"null\"").split(";;");
		RFSMatch rfsexactmatch = null;
		 //retreiving the PairesNamevalue of the exact Match :    ---> 
		
		for(String cc1: nameValuepaires1){
			
			String column_name = cc1.replace("\"", "").split(":")[0];
			String column_value ="";
			if(cc1.replace("\"", "").replace("longitude:","").isEmpty()){
			   column_value ="";
			}else{
				column_value =cc1.replace("\"", "").split(":")[1];
			}
			exactmatchinfostmp.add(new NameValuePair(column_name, column_value));
		
		}
		
		   // instanciate an extracted RFSMatch Object. of tpe exactmatch.
	        rfsexactmatch = new RFSMatch(key,exactmatchinfostmp , true);
		
		Vector<RFSExtraMatch> vectrfsextramatch = new Vector<RFSExtraMatch>();
		
		for(String ss: sectionsbruts_tab){
			
			String [] str_tmp = ss.split("\"rows\":");
			
			String sec_name = str_tmp[1].split("\"name\":")[1].replace("\"", "");
			String rows_str = str_tmp[1].replace("\\[\\{", "").replace("\\}\\]","").replace("\",\"", "\";;\"").replace("\"\"","\"null\"");
				
				
				Vector<RFSMatch> vectextramatchs = new Vector<RFSMatch>();
				String [] str_rows_tmp = rows_str.split("\\},\\{");
				for(String rr: str_rows_tmp){
					Vector<NameValuePair> elementmatch = new Vector<NameValuePair>();
					String [] nameValuepaires =  rr.split(";;");
					for(String cc: nameValuepaires){
						String column_name = cc.replace("\"", "").split(":")[0];
						String column_value =cc.replace("\"", "").split(":")[1];
						elementmatch.add(new NameValuePair(column_name, column_value));
						}
					RFSMatch rfsextramatch = new RFSMatch(key,elementmatch , false);//extramatch element
					vectextramatchs.add(rfsextramatch);
				}
				
				// Instanciate each extracted extra match RFSMatch Object. 
			    RFSExtraMatch rfsextramatch = new RFSExtraMatch(key,vectextramatchs , sec_name);
			    vectrfsextramatch.add(rfsextramatch);
			}
		RFSUM_res = new RFSUrlMatchs(key, vectrfsextramatch, rfsexactmatch, rfsmatchurlInfo);
      
		
		return RFSUM_res;
      }else{
    	  System.out.println("Problem :: Search keyword not found by the search factory");
    	  return null;
      }
		
		}
	
	public String getgeneratedFactoryURl(String key){
		if(!key.isEmpty()){
			this.SearchKeyword =key;
        this.FullFactoryUrl = FactoryUrlBase[0]+FactoryUrlBase[1]+key+FactoryUrlBase[2];
		}else{
			this.SearchKeyword = "downey";
			this.FullFactoryUrl = FactoryUrlBase[0]+FactoryUrlBase[1]+this.SearchKeyword+FactoryUrlBase[2];
		}
		return this.FullFactoryUrl;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getSearchKeyword() {		return SearchKeyword;	}
	public void setSearchKeyword(String searchKeyword) {		SearchKeyword = searchKeyword;	}

	public String[] getFactoryUrlBase() {		return FactoryUrlBase;	}
	public void setFactoryUrlBase(String[] factoryUrlBase) {	FactoryUrlBase = factoryUrlBase; }

	public RFSUrlMatchs getRfsum() {	return rfsum;	}
	public void setRfsum(RFSUrlMatchs rfsum) {		this.rfsum = rfsum;	}

	//public RFSEngine getRfsEngine() {		return rfsEngine;	}
	//public void setRfsEngine(RFSEngine rfsEngine) {		this.rfsEngine = rfsEngine;	}

}
