package redfinBots;

import java.util.Vector;

	public class RFSUrlMatchs {
		private String Keyword = "";
		private Vector<RFSExtraMatch>  allextramatchs ;
		private RFSMatch exactMatch;
		private String resultCode ="";
		private String errorMessage ="";
		private String version ="";
		
		public RFSUrlMatchs(String Key, Vector<RFSExtraMatch> AllExtra, RFSMatch Exact, Vector<String> inf){
			this.Keyword = Key;
			this.allextramatchs = AllExtra;
			this.exactMatch=Exact;
			this.resultCode = inf.get(0);
			this.errorMessage =inf.get(1);
			this.version =inf.get(2);
			
		}
		

		@SuppressWarnings("unused")
		private Vector<RFSExtraMatch> getExtraMatches_Bysection(final String section_name){
			Vector<RFSExtraMatch> res = new Vector<RFSExtraMatch>();
			Vector<RFSExtraMatch> allmatches = this.getAllextramatchs();
			for(RFSExtraMatch extramatch: allmatches){
				if(extramatch.getExtraMatchSectionName().contentEquals(section_name)){
					res.add(extramatch);
				}
			}
			return res;
		}
		
		
		
		public String getKeyword() {	return Keyword;	}
		public void setKeyword(String keyword) {	Keyword = keyword;	}
		
		public Vector<RFSExtraMatch> getAllextramatchs() {	return allextramatchs;	}
		public void setAllextramatchs(Vector<RFSExtraMatch> allextramatchs) {	this.allextramatchs = allextramatchs;	}
		
		public RFSMatch getExactMatch() {		return exactMatch;	}
		public void setExactMatch(RFSMatch exactMatch) {		this.exactMatch = exactMatch;	}
		
		public String getResultCode() {	return resultCode;	}	
		public void setResultCode(String resultCode) {	this.resultCode = resultCode;	}
		
		public String getErrorMessage() {	return errorMessage;	}
		public void setErrorMessage(String errorMessage) {	this.errorMessage = errorMessage;	}
		
		public String getVersion() {	return version;	}
		public void setVersion(String version) { this.version = version;}

		public static void main(String[] args) {
			// TODO Auto-generated method stub

		}

	}
