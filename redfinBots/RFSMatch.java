package redfinBots;

import java.util.Vector;

import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class RFSMatch {

	private String Keyword="";
	private String Subname="";
	private String Name="";
	private String NotificationBubbleValue="";
	private String Id="";
	private String Type="";
	private String Url="";
	private boolean ISExact=true;
	
	
	public RFSMatch(String key,Vector<NameValuePair> l, boolean typematch){
		this.Keyword = key;
	    this.ISExact = typematch;
		for(NameValuePair nv : l){
			if(nv.getName().equalsIgnoreCase("subname")){
				this.Subname = nv.getValue(); //l.get(0).getValue();
			}
			if(nv.getName().equalsIgnoreCase("name")){
			    this.Name = nv.getValue(); //l.get(1).getValue();
			}
			if(nv.getName().equalsIgnoreCase("NotificationBubbleValue")){
				this.NotificationBubbleValue = nv.getValue(); //l.get(2).getValue();
			}
			if(nv.getName().equalsIgnoreCase("id")){
			    this.Id = nv.getValue().split("_")[1]; //l.get(3).getValue().split("_")[1];
			}
			if(nv.getName().equalsIgnoreCase("type")){
			this.Type = nv.getValue();//l.get(4).getValue();
			}
			if(nv.getName().equalsIgnoreCase("url")){
			    this.Url = nv.getValue();//l.get(5).getValue();
			}
		}
	}
	
	
	public String getKeyword() {return Keyword;}
	public void setKeyword(String keyword) {Keyword = keyword;	}
	public String getSubname() {return Subname; }
	public void setSubname(String subname) {	Subname = subname;	}
	public String getName() {	return Name;}
	public void setName(String name) {Name = name;	}
	public String getNotificationBubbleValue() {return NotificationBubbleValue;	}
	public void setNotificationBubbleValue(String notificationBubbleValue) {	NotificationBubbleValue = notificationBubbleValue;}
	public String getId() {	return Id;	}
	public void setId(String id) {	Id = id;}
	public String getType() {	return Type;	}
	public void setType(String type) {	Type = type;	}
	public String getUrl() {	return Url;	}
	public void setUrl(String url) {	Url = url;	}
	public boolean isExact() {		return ISExact;	}
	public void setIsExact(boolean iSExact) {		ISExact = iSExact;	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
