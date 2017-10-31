package redfinBots;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Vector;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class RE_Object {
	private String href_link;
	private Vector<RE_Info> RE_Base_Infos;
	private Vector<String> RE_photos_UrlList = new Vector<String>();
    private Vector<RE_Detail_block> details_infos_blocks = new Vector<RE_Detail_block>();
    private Vector<Vector<RE_Info>> nearby_similars_infos_blocks = new Vector<Vector<RE_Info>>();
   
	
    @SuppressWarnings({ "unchecked", "serial" })
	public RE_Object(String RF_baseURL,String hreflnk, WebClient webc) {
    	RE_Base_Infos = new Vector<RE_Info>();
    	
    	 
    	 HtmlPage cp;
    	 Page p;
         if(hreflnk.isEmpty() || !hreflnk.contains("/")){
        	 System.out.println("No Link for this RE ");
         }
         else{	 
		   try {
			 
			p = webc.getPage(hreflnk);
			cp =(HtmlPage)p;
			webc.waitForBackgroundJavaScript(500000);
			
		
			List<HtmlElement> listele = (List<HtmlElement>) cp.getByXPath("//div[@class='sectionContainer']/div[@class='ab']/div[contains(@class,'HomeInfo')]");

			HtmlElement eleMainStatInlineBlock =  (HtmlElement) listele.get(0);
			
			HtmlElement eleTopStats =  (HtmlElement) eleMainStatInlineBlock.getByXPath("//div[@class='top-stats']").get(0);
			HtmlElement eleBottomStats =  (HtmlElement) eleMainStatInlineBlock.getByXPath("//div[@class='HomeBottomStats']").get(0);  // contains(@class,'bottom-stats inline-bloc')
			    HtmlElement eleTopStatsAddress = new HtmlElement("", cp, null) {	};
				@SuppressWarnings("unused")
				HtmlElement eleTopStatsAddressStreet = new HtmlElement("", cp, null) {	};
				@SuppressWarnings("unused")
				HtmlElement eleTopStatsAddressStreet_locality = new HtmlElement("", cp, null) {	};
				@SuppressWarnings("unused")
				HtmlElement eleTopStatsAddressStreet_Region = new HtmlElement("", cp, null) {	};
				@SuppressWarnings("unused")
				HtmlElement eleTopStatsAddressStreet_PostalCode = new HtmlElement("", cp, null) {	};
				HtmlElement eleTopStats_latitude = new HtmlElement("", cp, null) {	};
				HtmlElement eleTopStats_longitude = new HtmlElement("", cp, null) {	};
				HtmlElement eleTopStats_hotnessInfos = new HtmlElement("", cp, null) {	};
				HtmlElement eleTopStats_price = new HtmlElement("", cp, null) {	};
				HtmlElement eleTopStats_beds = new HtmlElement("", cp, null) {	};
				HtmlElement eleTopStats_baths = new HtmlElement("", cp, null) {	};
				HtmlElement eleTopStats_sqFt = new HtmlElement("", cp, null) {	};
				HtmlElement eleTopStats_sqFt_label = new HtmlElement("", cp, null) {	};
				
				List<HtmlElement> eleTopStats_h1spanList =  (List<HtmlElement>)eleTopStats.getByXPath("h1[@class='address inline-block']//span//span");
				
				//int ind_Topstatsspanadress =0;
				for(HtmlElement span: eleTopStats_h1spanList){
					if(span.hasAttribute("class") || span.hasAttribute("itemprop")){
						if(span.getAttribute("class").equalsIgnoreCase("adr") || span.getAttribute("itemprop").equalsIgnoreCase("address") ){
							eleTopStatsAddress = span;
							eleTopStatsAddressStreet = (HtmlElement)span.getByXPath("span[@itemprop='streetAddress']").get(0);
							eleTopStatsAddressStreet_locality = (HtmlElement)span.getByXPath("span/span[@itemprop='addressLocality']").get(0);
							eleTopStatsAddressStreet_Region = (HtmlElement)span.getByXPath("span/span[@itemprop='addressRegion']").get(0);
							eleTopStatsAddressStreet_PostalCode = (HtmlElement)span.getByXPath("span/span[@itemprop='postalCode']").get(0);
						}
					
						if(span.getAttribute("itemprop").equalsIgnoreCase("geo")){
							eleTopStats_latitude = (HtmlElement)span.getByXPath("meta[@itemprop='latitude']").get(0);
							eleTopStats_longitude = (HtmlElement)span.getByXPath("meta[@itemprop='longitude']").get(0);
						}
					}
					
				}
				
				List<HtmlElement> eleTopStats_divList =  (List<HtmlElement>)eleTopStats.getByXPath("div[contains(@class,'HomeMainStats home-info inline-block')]//div");
				//int ind_Topstatsdivs =0;
				for(HtmlElement div: eleTopStats_divList){
					if(div.hasAttribute("class") || div.hasAttribute("data-rf-test-id")){
						if(div.getAttribute("class").contains("info-block") && div.getAttribute("data-rf-test-id").equalsIgnoreCase("abp-price") ){
							 eleTopStats_price = (HtmlElement)div.getByXPath("div[@class='statsValue']").get(0);
						}
						if(div.getAttribute("class").contains("info-block") &&  div.getAttribute("data-rf-test-id").equalsIgnoreCase("abp-beds") ){
							eleTopStats_beds = (HtmlElement)div.getByXPath("div[@class='statsValue']").get(0);
						}
						if(div.getAttribute("class").contains("info-block") &&  div.getAttribute("data-rf-test-id").equalsIgnoreCase("abp-baths") ){
							eleTopStats_baths = (HtmlElement)div.getByXPath("div[@class='statsValue']").get(0);
						}
						if(div.getAttribute("class").contains("info-block") &&  div.getAttribute("data-rf-test-id").equalsIgnoreCase("abp-sqFt") ){
							eleTopStats_sqFt = (HtmlElement)div.getByXPath("span//span[@class='statsValue']").get(0);
							eleTopStats_sqFt_label = (HtmlElement)div.getByXPath("span//div[@class='statsLabel']").get(0);
							
							
						}
						
							
						}
					}
	
			
			//  Infos in the Bottom element
			List<HtmlElement> eleBottomStats_spanList = (List<HtmlElement>)eleBottomStats.getByXPath("div[contains(@class,'float-right more-info inline-bloc')]//span//span");//[@class='label']");
			HtmlElement eleBottomStats_builtyear =new HtmlElement("", cp, null) {	}; 
			HtmlElement eleBottomStats_lotsize = new HtmlElement("", cp, null) {	};
			HtmlElement eleBottomStats_cummulative = new HtmlElement("", cp, null) {	}; //
			HtmlElement eleBottomStats_onRedfin = new HtmlElement("", cp, null) {	};
			
			int ind_bottomspan =0;
			for(HtmlElement span: eleBottomStats_spanList){
				//System.out.println("Span  ==>\n"+span.asXml());
				if(span.getAttribute("class").equalsIgnoreCase("label")){
					if(span.getTextContent().contains("Built")){
						eleBottomStats_builtyear =	eleBottomStats_spanList.get(ind_bottomspan+1);
						//System.out.println("Span bult  ==>\n"+span.asXml());
					}
					if(span.getTextContent().contains("On Redfin")){
						eleBottomStats_onRedfin =	eleBottomStats_spanList.get(ind_bottomspan+1);					
										}
					if(span.getTextContent().contains("Cumulative")){
						eleBottomStats_cummulative =	eleBottomStats_spanList.get(ind_bottomspan+1);
					}
					if(span.getTextContent().contains("Lot Size")){
						eleBottomStats_lotsize =	eleBottomStats_spanList.get(ind_bottomspan+1);
					}
					
				}
				ind_bottomspan++;
			}
			//System.out.println("************");
			
			HtmlElement eleBottomStats_Status = (HtmlElement)eleBottomStats.getByXPath("//span//span//span[@class='value']").get(0);
			
			//System.out.println("**************************** Additional infos for real estat *****************************");
			
			RE_Base_Infos.add(new RE_Info("String", "FullAddress", eleTopStatsAddress.getTextContent()));
			RE_Base_Infos.add(new RE_Info("Double", "Latitude", eleTopStats_latitude.getAttribute("content")));
			RE_Base_Infos.add(new RE_Info("Double", "Longitude", eleTopStats_longitude.getAttribute("content")));
			RE_Base_Infos.add(new RE_Info("String", "HotnessInfo", eleTopStats_hotnessInfos.getTextContent()));
			RE_Base_Infos.add(new RE_Info("Integer", "Price", eleTopStats_price.getTextContent()));
			RE_Base_Infos.add(new RE_Info("Integer", "Beds", eleTopStats_beds.getTextContent()));
			RE_Base_Infos.add(new RE_Info("Double", "Baths", eleTopStats_baths.getTextContent()));
			RE_Base_Infos.add(new RE_Info("Integer", "SqFt", eleTopStats_sqFt.getTextContent().replace(",", "")));
			RE_Base_Infos.add(new RE_Info("Integer", "SqFt_Rate", eleTopStats_sqFt_label.getTextContent()));
			RE_Base_Infos.add(new RE_Info("Integer", "BuildYear", eleBottomStats_builtyear.getTextContent()));
			RE_Base_Infos.add(new RE_Info("Integer", "LotSize", eleBottomStats_lotsize.getTextContent().replace("Sq. Ft.", "").replace(",", "").trim()));
			RE_Base_Infos.add(new RE_Info("Integer", "Cummulative", eleBottomStats_cummulative.getTextContent().replace("days", "").trim()));
			RE_Base_Infos.add(new RE_Info("Integer", "OnRedfin", eleBottomStats_onRedfin.getTextContent().replace("days", "").replace("day", "").trim()));
			RE_Base_Infos.add(new RE_Info("String", "Status", eleBottomStats_Status.getTextContent()));
			
			
			
			HtmlElement ele_remarks_parent = (HtmlElement)cp.getByXPath("//section[contains(@class,'Section MainHouseInfoPanel')]"
																	+"/div[@class='sectionContainer']"
																	+"/div[@class='house-info-container']"
																	+ "/div[@class='house-info']"
																	+ "/div[@class='bhi']"
																	+ "/div[@class='content clear-fix']"
																	+ "/div[@class='remarks']").get(0);
			HtmlElement ele_remarks = new HtmlElement("", cp, null) {	};
			if(ele_remarks_parent.hasChildNodes()){
			   ele_remarks = (HtmlElement)ele_remarks_parent.getByXPath("p//span").get(0);
			}
			// System.out.println("Remarks :  "+ele_remarks.getTextContent());
			RE_Base_Infos.add(new RE_Info("String", "Remark", ele_remarks.getTextContent()));
			
			
			List<HtmlElement> ele_tab = (List<HtmlElement>)cp.getByXPath("//section[contains(@class,'Section MainHouseInfoPanel')]"
																	+"/div[@class='sectionContainer']"
																	+"/div[@class='house-info-container']"
																	+ "/div[@class='house-info']"
																	+ "/div[@class='bhi']"
																	+ "/div[@class='content clear-fix']"
																	+ "/div[@class='keyDetailsContainer']"
																	+ "/div[@class='keyDetailsList']"
																	+ "/div[@class='keyDetail']" 
																	);
			Vector<RE_Info> tt_left = new Vector<RE_Info>();
			for (final HtmlElement rrr : ele_tab) {
			
				String Info_Name = ((HtmlElement)rrr.getByXPath("span[@class='header']").get(0)).getTextContent();
				String Info_Value = ((HtmlElement)rrr.getByXPath("span[@class='content']").get(0)).getTextContent();
				tt_left.add(new RE_Info("String",Info_Name, Info_Value));
			}	    
			
			/* System.out.print("Home Infos On Table:  [");//+tt_left.toString());
					for(RE_Info leftinf:tt_left){
						System.out.print(leftinf.toString());
					}  */
			
			
			RE_Base_Infos.addAll(tt_left);
	
			
			String ListingSource =  ((HtmlElement)cp.getByXPath("//section[contains(@class,'Section MainHouseInfoPanel')]"
																	+"/div[@class='sectionContainer']"
																	+"/div[@class='house-info-container']"
																	+ "/div[@class='house-info']"
																	+ "/div[@class='bhi']"
																	+ "/div[@class='content clear-fix']"
																	+ "/div[@class='listing-source']"
																	+ "/p[@class='source-content']/span").get(0)).getTextContent();
			
			//System.out.println("Infos listing source :  "+ (new NameValuePair("Listing Source", ListingSource)).toString());
			RE_Base_Infos.add(new RE_Info("String","ListingSource", ListingSource ));
			
			
			
			List<HtmlElement> lll = (List<HtmlElement>)cp.getByXPath("//section[contains(@class,'Section MainHouseInfoPanel')]"
																	+"/div[@class='sectionContainer']"
																	+"/div[@class='house-info-container']"
																	+ "/div[@class='house-info']"
																	+ "/div[@class='bhi']"
																	+ "/div[@class='content clear-fix']"
																	+ "/div[@class='inline-block agent-info-container']"
																	+ "/div/div/div/div");
			String AgentListingInfos ="";
			for (final HtmlElement ee : lll) {
				if(!AgentListingInfos.isEmpty()){
					AgentListingInfos = AgentListingInfos+";;"+ee.getTextContent();
				}else{	AgentListingInfos = ee.getTextContent();	}
			}
			//System.out.println("Infos listing Agent :  "+ AgentListingInfos);
			RE_Base_Infos.add(new RE_Info("String","ListingAgent", AgentListingInfos ));
			
			
			// affichage of all extracted RE details blocks:
			
			// Proprety Details extraction:

			List<HtmlElement> listblocks_details = (List<HtmlElement>)cp.getByXPath( "//div/div[contains(@class,'delayLoadedComponentWrapper')]"
																				    + "/div/section[@id='property-details-scroll']" // or use // "/div/section[contains(@class,'AmenitiesInfoSection')]" 
																				    + "/div[@class='sectionContainer']"
																			       	+ "/div[@class='main-content']"
																			      	+   "/div/div[@class='amenities-container']/div"); 

			
			//int nbblocks=0;
			for(HtmlElement blckdetail: listblocks_details){
				//System.out.println("block  detail  :   \n"+blckdetail.asXml());
				
				Vector<NameValuePair> blockdetail = new Vector<NameValuePair>();
				String blocktitle= ((HtmlElement)blckdetail.getByXPath("div[@class='super-group-title']").get(0)).getTextContent();
				blockdetail.add(new NameValuePair("BlockTitle",blocktitle));
				List<HtmlElement> lsteleinblck_temp = (List<HtmlElement>)blckdetail.getByXPath("div[@class='super-group-content']/div[@class='amenity-group']");
				for(HtmlElement eleinblck : lsteleinblck_temp){
					String Info_detail_Name_temp = ((HtmlElement)eleinblck.getByXPath("ul/div/h4[@class='title']").get(0)).getTextContent();
				//	blockdetail.add(Info_detail_Name_temp);
					List<HtmlElement> listLiele = (List<HtmlElement>) eleinblck.getByXPath("ul/div/li");
					String det="";
					for(HtmlElement det_data: listLiele){
						if(det.isEmpty()){
							det =  det_data.getTextContent()+";;";	
						}else{det = det+det_data.getTextContent()+";;";}
						
				
					}
					blockdetail.add(new NameValuePair(Info_detail_Name_temp, det));
				
					// affichage of all extracted RE details blocks:
					  
				}
				//System.out.println("Block "+blocktitle+" --> details vector ---> : "+ blockdetail.toString());

				RE_Detail_block detblk = new RE_Detail_block(blockdetail);
				details_infos_blocks.add(detblk);
				
			}
			
			String [] ttthref = hreflnk.split("/");
			String propretyid="";
			for(int i=0; i<ttthref.length;i++){
				if(((String)ttthref[i]).contains("home")){
					propretyid = (String)ttthref[i+1];
				}
			}
	
			
			//System.out.println("proprety_id =  "+propretyid );
			RE_Base_Infos.add(new RE_Info("String","PropretyID", propretyid));
		
			
			// *******************************************************************************************
			
			// Instead of locating running the javascript that load the view port of nearby elements I realized 
			// that these elements are contained also in the similar nearby homes page section: 
			
			// *******************************************************************************************
			
			
			
			
			List<HtmlElement> similarhomeslist = (List<HtmlElement>)cp.getByXPath(  
										"//div/div[contains(@class,'delayLoadedComponentWrapper')]"
										+ "/div/section[contains(@class,'HigherSimilarsSection')]"
										+ "/div[@id='nearby-similar-homes']");/*
										//+ "//div"
										+ "/div[contains(@class,'main-content')]"
										+ "/div[contains(@class,'HorizontalScrollingController')]"
										+ "/div[@class='HorizontalScrollingViewport']"
										+ "/ul/li[@class='visible']"
										+ "/div[@class='SimilarHomeCardReact']"
										+ "/div[contains(@class,'HomeCardV2 card-')]"
										+ "/div[@class='basic-card']"
										+ "/div[contains(@class,'card-content clickable')]"
										+ "/div[@class='photos']"
										+ "/a[@class='link']"); */
		//	System.out.println("Similar homes number  : "+ similarhomeslist.size());
		//	System.out.println("Similar homes element  : "+ similarhomeslist.get(0).asXml());
			for(@SuppressWarnings("unused") HtmlElement  similarhome: similarhomeslist){
		//		System.out.println("Similar home  : "+ similarhome.asXml());
				
			}
			
			
			

 // **********************************************************************************************************************************************************   
 //		                                               RE Photos Fetching 																	
 // **********************************************************************************************************************************************************   
			
			
	// Infos Image Elements
			// fetching photos base name and Url from from redfin result page additonal link :
			// find the img node we are looking for:
		
			 List<HtmlElement> imgParentElements = (List<HtmlElement>) cp.getByXPath("//section[contains(@class,'Section MediaBrowserSection')]"
                                         +  "/div[contains(@class,'MediaBrowser normal-screen photo-selected')]"
					                     +  "/div[contains(@class,'PhotoArea')]/div[contains(@class,'CrossFadeStack')]"
                                         +  "/span[contains(@class,'FadeItem')]/div[@class='ImageCard']");

          HtmlImage img =null;
          

          File Fileimage_0  =null;
			for(HtmlElement imparentele: imgParentElements){

											 if(imparentele.getByXPath("img[contains(@src,'https://ssl.cdn-redfin.com/photo/')]").isEmpty()){
												 if(!(imparentele.getByXPath("img[contains(@alt,'"+eleTopStatsAddress.getTextContent()+"')]")).isEmpty()){
													 img = (HtmlImage) imparentele.getByXPath("img[@alt='"+eleTopStatsAddress.getTextContent().toString()+"']").get(0);
												//	 System.out.println("imagenode 1 :\n "+ img.asXml());
												 }
											  else{
										       img = (HtmlImage) imparentele.getByXPath("img[contains(@src,'https://ssl.cdn-redfin.com/')]").get(0);
										      // System.out.println("imagenode 2 :\n "+ img.asXml());
			                                    }
											 }
											 
										//	 System.out.println("image corresponding adress :\n "+ eleTopStatsAddress.getTextContent());

										// get the src attribute containing the Photo url and extract the name of the photo 0 with the prefix "_0"
										String  src_att = img.getAttribute("src");
									//	System.out.println("src image Photo  : "+ src_att);//img.getAttribute("src"));
										
										
										String photo0_name =src_att.split("/")[7].replace(".jpg", "");
										
										//the name of the originale photo without the prefix "_0"
										String  photos_basename = photo0_name.replace("genMid.", "").split("_")[0];
										@SuppressWarnings("unused")
										String  photos_baseindex=   photo0_name.replaceAll(photo0_name.split("_")[0],"");
										
										// create the directory and file to download the photos and download the first one from the link to copy it locally:

										Fileimage_0 = new File("./imres/"+photos_basename+"/"+photo0_name.replace("genMid.", "")+".jpg");
										Fileimage_0.getParentFile().mkdirs();
										//}
										img.saveAs(Fileimage_0);
			}
			
			
			} catch (FailingHttpStatusCodeException e) {e.printStackTrace();
			} catch (MalformedURLException e) {e.printStackTrace();
			} catch (IOException e) {e.printStackTrace();}
			
	    }

	    	
		}
			
			
				    	
	public static void main(String[] args) {
		
		
	}
	
	@SuppressWarnings("unused")
	private Vector<String> get_RE_photos(){
		return RE_photos_UrlList;
	}
	
	@SuppressWarnings("unused")
	private Vector<Vector<RE_Info>> getNearby_SimilarsInfosBlocks(){
		return nearby_similars_infos_blocks;
	}
	@SuppressWarnings("unused")
	private Vector<RE_Detail_block> getDetailsInfos_Blocks(){
		return details_infos_blocks;
	}
	private RE_Info getBaseInfobyname(String name) {
		RE_Info res=null;
		for(RE_Info inf: RE_Base_Infos){
			if(inf.getRE_info_Name().equalsIgnoreCase(name)){
				 res= inf;
			}
		}
		return res;
	}
	public long getPropretyId() {return (Long) (getBaseInfobyname("PropretyId").getRE_info_Value());}
	public String getHref_link() {return href_link;	}
	public String tostring(){
		String res="";
		for(RE_Info baseinfo: this.RE_Base_Infos){
			res = res + baseinfo.getRE_info_Name()+"   "+baseinfo.getRE_info_Value()+"   "+baseinfo.getRE_info_Type()+"\n";
		}
		res = res + "******************************************************"+"\n";
		res = res + "************ More Details and Features ***************"+"\n";
		res = res + "******************************************************"+"\n";
		
		for(RE_Detail_block detailinfoblock: this.details_infos_blocks){
			
			res = res + detailinfoblock.getDetailBlockTitle()+" : \n  ";
			
			Vector<RE_Info> temp = detailinfoblock.getFeatureDetails();
			
			for(RE_Info detailinfo: temp){
				res = res + detailinfo.getRE_info_Name()+"   "+detailinfo.getRE_info_Value()+"   "+detailinfo.getRE_info_Type()+"\n";
			}
			
			
		}
		
		//here add to res the Image URl infos and nearby RE's:
		
		return res;
	}
	
	
}
