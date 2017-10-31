package redfinBots;

import java.util.*;

import com.gargoylesoftware.htmlunit.util.NameValuePair;



public class RE_Info {
	private String RE_info_Type;
	private String RE_info_Name;
	private Object RE_info_Value;
	//private String  Parent_RE_parseLongpropID ;
	
	
	// types list is {Integer, Double, Long , String , vect_nvp = vector<NameValuePair>}
	
	public RE_Info(String type, String name, String value) {
		// the value String has to be Sanitized First by removing the annoying Characters for our processing from it:
		value = value.replace("â€”","").replace("$", "").replace(",", "").replace("/", "").replace("Sq. Ft.", "").replace("days", "").replace("day", "").replace("—", " ").trim();
		
		// We start by instantiating The attribute Type and Name of the RE_info object
		this.RE_info_Type = type;
		this.RE_info_Name =  name;
		
		// Then we instantiate the value of the the RE_object after checking its consistency(is it empty or illegal value?? if not then we instantiate it as Value of the Type (type))
		
		
		if(type.contentEquals("Integer")){
			boolean already_processed=false;
			if(value.contentEquals("Unknown") || value.isEmpty()){
				this.RE_info_Value =0;
				already_processed=true;
			}
			if(!already_processed && value.contains("Acres")){
				//value = value.replace("Acres", "").trim();// *43560
				this.RE_info_Value =(int)Double.parseDouble(value.replace("Acres", "").trim())*43560;
				already_processed=true;
			}	
			if(!already_processed ){//&& !value.contains("Acres")){
				//System.out.println("Problematic value to parse to int :     "+  value);
				if(value.contains("—")){ 
					this.RE_info_Value = " ";  
				}else{
				    this.RE_info_Value =(int) Integer.parseUnsignedInt(value);
				}
			}
			}
		if(type.contentEquals("Double")){
			//System.out.println("Problematic value to parse to int :     "+  value);
			if(value.isEmpty()){
				this.RE_info_Value = (double) 0;	
			}else{
			this.RE_info_Value =(double)Double.parseDouble(value);
			}
			}
		if(type.contentEquals("Long")){this.RE_info_Value = (long)Long.parseLong(value);	}
		if(type.contentEquals("String")){this.RE_info_Value = value;	}
		if(type.contentEquals("vect_nvp")){
			String [] Vect_str= value.split(";;");
			Vector<NameValuePair> subblckinfos = new Vector<NameValuePair>();
			for(String st:Vect_str){
				if(!st.isEmpty()){
					String valuetemp="";
					if(st.contains(":")){
						valuetemp=(String)st.split(":")[1];
						subblckinfos.add(new NameValuePair((String)st.split(":")[0],valuetemp));
					}else{
					subblckinfos.add(new NameValuePair("ParentName",st));
					}
					
				}
			}
			
			// This is my final RE_info_value object instantiated as a vector of <NameValuePair> Fully saved in one vector to return { this.RE_info_Value. }
			this.RE_info_Value = subblckinfos;	}
		
	}

	public static void main(String[] args) {
		

	}

	public String getRE_info_Type() {	return RE_info_Type;	}
	public void setRE_info_Type(String rE_info_Type) {	RE_info_Type = rE_info_Type;	}

	public String getRE_info_Name() {return RE_info_Name;	}
	public void setRE_info_Name(String rE_info_Name) {	RE_info_Name = rE_info_Name;	}

	public Object getRE_info_Value() {	return RE_info_Value;	}
	public void setRE_info_Value(Object rE_info_Value) {RE_info_Value = rE_info_Value;	}
    
	public String toString(){
		return this.RE_info_Name
				+"["
				+this.RE_info_Type
				+"] = "
				+this.RE_info_Value;
		}
	

}
