package eney.domain;



public class ReportingVO {
	
	private String APPLICATION_NAME = "analyticstest-e31cd6bab2a7.json";
	//development
	private String KEY_FILE_LOCATION = "C:/var/keystore";
	//beta , production 
	//private String KEY_FILE_LOCATION = "/usr/local/keystore";
	private String VIEW_ID = "103612926";
	
	public String getAPPLICATION_NAME() {
		return APPLICATION_NAME;
	}
	public void setAPPLICATION_NAME(String aPPLICATION_NAME) {
		APPLICATION_NAME = aPPLICATION_NAME;
	}
	public String getKEY_FILE_LOCATION() {
		return KEY_FILE_LOCATION;
	}
	public void setKEY_FILE_LOCATION(String kEY_FILE_LOCATION) {
		KEY_FILE_LOCATION = kEY_FILE_LOCATION;
	}
	public String getVIEW_ID() {
		return VIEW_ID;
	}
	public void setVIEW_ID(String vIEW_ID) {
		VIEW_ID = vIEW_ID;
	}
	@Override
	public String toString() {
		return "ReportingVO [APPLICATION_NAME=" + APPLICATION_NAME + ", KEY_FILE_LOCATION=" + KEY_FILE_LOCATION
				+ ", VIEW_ID=" + VIEW_ID + "]";
	}
}
