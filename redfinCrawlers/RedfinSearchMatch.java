package redfinCrawlers;

import java.util.Vector;

import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class RedfinSearchMatch {

	private String Keyword="";
	private String Subname="";
	private String Name="";
	private String NotificationBubbleValue="";
	private String Id="";
	private String Type="";
	private String Url="";
	private boolean ISExact=true;
	
	private RedfinSearchMatch(String key,Vector<NameValuePair> l, boolean typematch){
		this.Keyword = key;
		this.Subname = l.get(0).getValue();
		this.Name = l.get(1).getValue();
		this.NotificationBubbleValue = l.get(2).getValue();
		this.Id = l.get(3).getValue();
		this.Type = l.get(4).getValue();
		this.Url = l.get(5).getValue();
		this.ISExact = typematch;
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
