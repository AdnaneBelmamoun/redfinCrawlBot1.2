package redfinBots;

	import java.util.Vector;

	public class RFSExtraMatch {
	    private String keyword= ""; 
	    private String ExtraMatchSectionName ="";
		private Vector<RFSMatch> extramatchs ;
		
		public RFSExtraMatch(String key,Vector<RFSMatch> extramtches, String sectionname){
			this.keyword = key;
			this.ExtraMatchSectionName = sectionname;
			this.extramatchs = extramtches;
		}
		
		
		public String getKeyword() {		return keyword;	}
		public void setKeyword(String keyword) {	this.keyword = keyword;	}
		public String getExtraMatchSectionName() {	return ExtraMatchSectionName;	}
		public void setExtraMatchSectionName(String extraMatchSectionName) {	ExtraMatchSectionName = extraMatchSectionName;	}
		public Vector<RFSMatch> getExtraMatchs() {	return extramatchs;	}
	    public void setExtralMatchs(Vector<RFSMatch> match) {	this.extramatchs = match;	}

		public static void main(String[] args) {
			// TODO Auto-generated method stub

		}

	}
