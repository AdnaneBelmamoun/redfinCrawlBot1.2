package redfinBots;

import java.util.Vector;

import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class RE_Detail_block {
	private String DetailBlockTitle;
	private Vector<RE_Info> features_detail;
	
	public RE_Detail_block(Vector<NameValuePair> blockvect) {
		this.DetailBlockTitle = ((NameValuePair)blockvect.get(0)).getValue();
		features_detail = new Vector<RE_Info>();
	    for(NameValuePair nvp: blockvect){
	    	if(nvp.getName().contentEquals("BlockTitle")){	this.setDetailBlockTitle(nvp.getValue());}
	    		//features_detail.add(new RE_Info("String","SubSectionTitle", nvp.getValue())); 	}
	    	else{
	    		features_detail.add(new RE_Info("vect_nvp",nvp.getName(),nvp.getValue()));
	    	}
	    }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getDetailBlockTitle() {return DetailBlockTitle;	}
	public void setDetailBlockTitle(String detailBlockTitle) {	DetailBlockTitle = detailBlockTitle;	}
	
	public Vector<RE_Info> getFeatureDetails() {return features_detail;	}
	public void setFeatureDetails(Vector<RE_Info> detailBlock) {	features_detail = detailBlock;	}
	
	public RE_Info getFeatureDetailsByName(String featurename) {
		RE_Info res = null;
		for(RE_Info feature: features_detail){
			if(feature.getRE_info_Name().equalsIgnoreCase(featurename)){
				res = feature;
			}
		}
		return res;	}

}
