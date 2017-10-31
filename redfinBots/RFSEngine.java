package redfinBots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;



// This Class contains all the necessary methods to extract url's and search data from Redfin server

public class RFSEngine {
	
	private WebClient client ;
	private String CurrentSearchKey ;
	private String RF_base_URL;
	String [] urlprefix = {"#!v=8&sst=&region_id=","&region_type=","&market=socal&"} ;
	
	
	public RFSEngine(){
		this.client = getWebClient();
		setRF_base_URL("https://www.redfin.com");
	}
	
	public RFSEngine(String CurrentSearchKey){
		this.client = getWebClient();
		this.setRF_base_URL("https://www.redfin.com");
		this.CurrentSearchKey = CurrentSearchKey;
	}
	
	public WebClient getWebClient(){
		WebClient wc =  	new WebClient(BrowserVersion.FIREFOX_38);
		CookieManager cookieManager = wc.getCookieManager();
	    cookieManager.setCookiesEnabled(true);
		wc.getCookieManager().setCookiesEnabled(true);
		//client.setJavaScriptEngine(new JavaScriptEngine(client));
		wc.getOptions().setJavaScriptEnabled(false);
		wc.getOptions().setRedirectEnabled(false);
		wc.getOptions().setGeolocationEnabled(true);
		wc.getOptions().setCssEnabled(true);
		wc.getOptions().setThrowExceptionOnScriptError(true);
		wc.setAjaxController(new NicelyResynchronizingAjaxController());
		wc.getOptions().setTimeout(40000);
		
		this.client = wc;
		
	return wc; 
	}
	

	 
	/*
	 *  Method Prepare Matches has to instantiate RFSFactory object to get brute string match
	 *  then parse it by the method parsRFSfactoryData(String BrutMatchStr) also here in RFSEngine. 
	 */
	public Vector<Vector<String>> preparesearchMatches(String searchk){
		Vector<Vector<String>> table_res_data = new Vector<Vector<String>>();
         /*
          *  Parse Search String key k into URLString 
          */
		
		// here urlencode() can do the same job
		  String k = searchk.replace(" ", "%20").replace(",", "%2c");
		  
		/*
		 *  Create an instance of the RFSFactory that take the current WClient and the search key
		 */
		RFSFactory  rfsfact = new RFSFactory(k, this.getClient());
		/*
		 *  Get Redfin Url search match from the factory 
		 */
	    RFSUrlMatchs rfsurlm = rfsfact.getRfsum();	
        String[] BaseUrl = rfsfact.getFactoryUrlBase();
     
        String [] urlcomp = {BaseUrl[0] //"https://www.redfin.com"
				        		,rfsurlm.getExactMatch().getUrl()//+"/"
				        		,urlprefix[0],rfsurlm.getExactMatch().getId()
				        		,urlprefix[1],rfsurlm.getExactMatch().getType()
				        		,urlprefix[2]};
        
        // Initial search result url:
        String initUrl = urlcomp[0]+urlcomp[1]+urlcomp[2]+urlcomp[3]+urlcomp[4]+urlcomp[5]+urlcomp[6];
  
        // Call the first result page 
        HtmlPage currentPage =null;
        try {
        	currentPage= this.getClient().getPage(initUrl);
			this.getClient().waitForBackgroundJavaScript(500000);
		
		} catch (FailingHttpStatusCodeException e) {e.printStackTrace();
		} catch (MalformedURLException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();		}
        
       /* ****************** */
        /*Here Major Upgrade*/
        /* ****************** */ 
        // Due to website update it becomes easier to just get the entire table from all the pages in one xls file:
        // let us go ahead and do it :
        
     
		 HtmlElement downloadbutton = (HtmlElement) currentPage.getByXPath("//div[@class='downloadAndSave']/div[@class='viewingPage']/a[@class='downloadLink']").get(0) ;
		 
		 String urlString =  RF_base_URL+downloadbutton.getAttribute("href");
		 
		 File csvfile = new File("E:/testredfin/resultestredfin2updated.csv");
		 
		 boolean datacreated = new NetFile(csvfile,urlString, -1).load(); //returns true if succeed, otherwise false.
		
		 if(datacreated){
			 // Now we need to read the collected data from the xls collected file (containing the totality of our search results):
			 // assuming the data organization is still the same, i will just parse to a matrix that fit the Vector<Vector<String>> table_res_data
			 // for such XLS file reads i use POI Apache API for Microsoft documents:
			 
			 String Datarowstr = "";
			 String headersrow = "";
		     String cvsseparator = ",";
		     
		    // Vector<Vector<String>> DataMatrix = new Vector<Vector<String>>();

		     BufferedReader br = null; 
		     Vector<String> headervector = new Vector<String>();
		     
		     try {
		    	 
		    	 br =  new BufferedReader(new FileReader(csvfile));
		    	 Stream<String> linestream0 = br.lines();
		    	 
		    	 //System.out.println("line read test :  "+ linestream.findFirst().get().toString());
		    	 String[] headerscellslist = null;
		    	 
		    	 // read first row and get headers as they are always in the first line:
		        if(!(headersrow = linestream0.findFirst().get().toString()).isEmpty()){
		        	 headerscellslist = headersrow.split(cvsseparator);
		        	
		            
		            for(int i=0; i<headerscellslist.length; i++){
		            	String tempheader = (String)headerscellslist[i];
		            	if(tempheader.equalsIgnoreCase("Address") || tempheader.equalsIgnoreCase("Location") ||
		            	   tempheader.equalsIgnoreCase("Beds") || tempheader.equalsIgnoreCase("Baths")||
		            	   tempheader.contentEquals("$/SQUARE FEET") || tempheader.contains("DAYS") ||
		            	   tempheader.equalsIgnoreCase("Price") || tempheader.equalsIgnoreCase("SQUARE FEET")||
		            	   tempheader.contains("URL") ){
		            	headervector.add(headerscellslist[i]);
		            	}
		            }
		     
		    
		            // Then add the entire headers vector to the matrix of data always at the first row position of the matrix:
		            table_res_data.add(0,headervector);
		        		
		        }
		        	
		        Stream<String> linestream = br.lines();
		        // Then read the rest of real data rows
		        Iterator<String> linestreamiter = linestream.skip(0).iterator();
		        while (linestreamiter.hasNext() && !((Datarowstr=linestreamiter.next()).toString().isEmpty()) ) {

		        
		        	
		        	//need small processing to overcome splitter existence inside cell data
		        	// --->case of cell data strings containing "," like in Location data ("4 - Downtown Area, Alamitos Beach")
		        	// Datarowstr
		        	String correctedDataRowString ="";
		        	if (Datarowstr.contains(",\"")) {
		        		String problematicString = Datarowstr.substring((int)Datarowstr.indexOf(",\"")+1,(int)Datarowstr.indexOf("\",")+1) ;
		        		
		        		
		        		String rep0 = problematicString.replaceAll(","," ");
		        		String rep1 = rep0.replace("(","");
		        		String rep2 = rep1.replace(")","");
		        		String Replacment = rep2.replaceAll("\"","");

		        		correctedDataRowString = Datarowstr.replace(problematicString,Replacment);
		        		
		        		Datarowstr = correctedDataRowString ;
		        		
		        		
		        	}else{
		        		
		        	}
		        	// finally i Update my DataRow String with the correct expression
		            String[] cellslist = Datarowstr.split(cvsseparator);
		            Vector<String> Datarowvect = new Vector<>();
		            
		            for(int i=0; i<cellslist.length; i++){
		            		if(headervector.contains((String)headerscellslist[i])){
		            			if(((String)cellslist[i]).isEmpty()){
		            				cellslist[i] = " ";
		            			}
		            			Datarowvect.add(headervector.indexOf((String)headerscellslist[i]),(String)cellslist[i]);
		            		}
		            }
                
		            // Then add the entire Datarow vector to the matrix of data:
		          
		            table_res_data.add(Datarowvect);
		            
		            }

		        } catch (IOException e) {    e.printStackTrace();       }

			 
		 }// end process id data were created on xls temp file
		 
		
        
    	return table_res_data;
	}
	
	
	 public Vector<Vector<String>> getTableData(HtmlPage cp){
		 Vector<Vector<String>> table_res_data =  new Vector<Vector<String>>();
		 cp.getWebClient().getOptions().setJavaScriptEnabled(true);
		 cp.getWebClient().getOptions().setRedirectEnabled(false);
		 cp.getWebClient().getOptions().setGeolocationEnabled(false);
		 cp.getWebClient().getOptions().setCssEnabled(false);
		 cp.getWebClient().getOptions().setThrowExceptionOnScriptError(true);
		 cp.getWebClient().setAjaxController(new NicelyResynchronizingAjaxController());
		 cp.getWebClient().getOptions().setTimeout(108000);
		 try {
			cp = cp.getWebClient().getPage(cp.getUrl());
		} catch (FailingHttpStatusCodeException e2) { e2.printStackTrace();
		} catch (MalformedURLException e2) {  e2.printStackTrace();
		} catch (IOException e2) {  e2.printStackTrace();		}
		 
		     
	      
		 HtmlElement buttonspan = (HtmlElement) cp.getByXPath("//button[starts-with(@class,'ModeOption')]/span[@data-rf-test-name='tableOption']").get(0) ; 
		
		 HtmlButton button = (HtmlButton)buttonspan.getParentNode();
		 
		 HtmlElement downloadbutton = (HtmlElement) cp.getByXPath("//div[@class='downloadAndSave']/div[@class='viewingPage']/a[@class='downloadLink']").get(0) ; 
		
			 
		 String urlString =  RF_base_URL+downloadbutton.getAttribute("href");
		 
		 new NetFile(new File("E:\\testredfin/resultestredfin2.csv"),urlString, -1).load(); //returns true if succeed, otherwise false.
		 
	     cp = cp.getPage();
		
		
		 UnexpectedPage r = null; 
			try {
				
				r = button.click();
				
				cp.getWebClient().waitForBackgroundJavaScript(500000);
					
							System.out.println("Status Code   :  "+r.getWebResponse().getStatusCode()) ;
							System.out.println("Response   :  "+r.getWebResponse().getStatusCode()) ;
							System.out.println("Content click result   :  "+r.getWebResponse().getContentType());
							//System.out.println(cp.getWebClient().getCurrentWindow().getJobManager().getEarliestJob().toString()) ;
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		
		@SuppressWarnings("unused")
		HtmlElement homeviewdiv = (HtmlElement) cp.getByXPath("//div[starts-with(@class,'HomeView')]").get(1) ;// /div[contains(@data-rf-test-id,'table-view')]
		this.getClient().waitForBackgroundJavaScript(3000);

		 @SuppressWarnings({ "unchecked", "unused" })
		 List<HtmlElement> list_rows_ele_div_a =  (List<HtmlElement>) cp.getByXPath("//table/tbody/tr/td/div/a[contains(@data-reactid,'.$TableRowColumn_')]");
		 @SuppressWarnings("unchecked")
		List<HtmlTableRow> list_rows_data =  (List<HtmlTableRow>) cp.getByXPath("//table/tbody/tr[starts-with(@id,'ReactDataTableRow_')]");
		 int row_ind= 0;
		 String hreflink ="";
         for (final HtmlTableRow ele_row_data : list_rows_data) {
        	 
			 Vector<String> RE_ini_Row= new Vector<String>();	
			 
        	 @SuppressWarnings("unused")
			Instant strt = Instant.now();

        	    HtmlElement row_ele_div_add = (HtmlElement) ele_row_data.getByXPath("td/div[@class='address']").get(0);
				 if(!((HtmlElement)row_ele_div_add.getFirstChild()).getTagName().contentEquals("a")){
					 hreflink = "Event_RE_Open_Days_NoUrl";
					// System.out.println("No Data Link for the row "+(row_ind)+": ----> "+ hreflink);
					 RE_ini_Row.add(hreflink);
				 }
				 else{
			     HtmlElement row_ele = (HtmlElement)row_ele_div_add.getByXPath("a[contains(@data-reactid,'.$TableRowColumn_')]").get(0); //(HtmlElement) list_rows_ele_div_a.get(row_ind);
				 hreflink =row_ele.getAttribute("href");

				   RE_ini_Row.add(hreflink);
			 }
        	
			 
			 
			 //here adding cells of each row in the result table
			   for(int i=1; i<9 ;i++){
				 String celltemp = ele_row_data.getCell(i).getTextContent();
				 String cellele="";
				 // special processing to delete the mention "listed by" 
				 if(celltemp.contains("Listed by:")){
					 cellele = celltemp.split("Listed by:")[0];
					 }else{cellele = celltemp;}
				 RE_ini_Row.add(cellele);
				 }
			   System.out.println("Data Row "+(row_ind)+": ----> "+ RE_ini_Row);
			 table_res_data.add(RE_ini_Row);
			 
			 row_ind++;
			 }
		 return table_res_data;
		 }
	
	
	 public static Vector<String> getTableHeaders(HtmlPage cp){
		 Vector<String> header_vect =  new Vector<String>();
		 @SuppressWarnings("unchecked")
		List<HtmlTableRow> list_columns =  (List<HtmlTableRow>) cp.getByXPath("//table//thead//tr[starts-with(@class,'tableTitle')]");
		  System.out.println("Table Headers : ");
		 for (final HtmlTableRow ele_row_data : list_columns) {
			 header_vect.add("href");
			 for(int i=1; i<9 ;i++){
				 String cellhead = ele_row_data.getCell(i).getTextContent();
				 System.out.println(cellhead);
				 header_vect.add(cellhead);
				 }
			 }
		 System.out.println(header_vect);
		 return header_vect;
		 }
	
	 
	 public static int getnumberpages(HtmlPage cp){
		 int  res = 1;
		 @SuppressWarnings("unchecked")
		 HtmlElement ele =  ((List<HtmlElement>) cp.getByXPath("//div[@class='viewingPage']/span[@class='pageText']")).get(0);

		 String res_str = ele.getTextContent();
		 
		  res = Integer.parseInt(res_str.split("of")[1].trim());

		 return res;
		 }
	 
	 public static void downloadFileFromURL(String urlString, File destination) {    
	        try {
	            URL website = new URL(urlString);
	            ReadableByteChannel rbc;
	            rbc = Channels.newChannel(website.openStream());
	            FileOutputStream fos = new FileOutputStream(destination);
	            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	            fos.close();
	            rbc.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	

	public static void main(String[] args) {
		
		String searchkey = "van nuys, ca";
		RFSEngine rfseng =  new RFSEngine();
		Vector<Vector<String>> res = rfseng.preparesearchMatches(searchkey);
		
  		   for(final Vector<String>  rowww:res){
 			 System.out.println(rowww.toString());
 			}
        
		      
	}

	public WebClient getClient() { 	return client;	}
	public void setClient(WebClient client) {		this.client = client;	}

	public String getCurrentSearchKey() {	return CurrentSearchKey;	}
	public void setCurrentSearchKey(String currentSearchKey) {	CurrentSearchKey = currentSearchKey;  }

	public String getRF_base_URL() {
		return RF_base_URL;
	}

	public void setRF_base_URL(String rF_base_URL) {
		RF_base_URL = rF_base_URL;
	}

}
