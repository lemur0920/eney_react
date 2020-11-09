package eney.domain;

public class MobilUserCertifyResponseVo {

	public static final String SUCCESS_RESULTCD = "0000";
	
	private String svcid;
	private String mobilid;
	private String signdate;
	private String tradeid;
	
	private String name;
	private String name2;
	private String no;
	private String commid;
	private String resultcd;
	private String resultmsg;
	
	private String cryptyn;
	private String keygb;
	
	private String socialno;
	private String sex;
	private String foreigner;
	
	private String ci;
	private String di;
	
	private String CI_Mode;
	private String CI_Code;
	private String mac;
	
	private String MSTR;

	public String getSvcid() {
		return svcid;
	}
	public void setSvcid(String svcid) {
		this.svcid = svcid;
	}
	public String getMobilid() {
		return mobilid;
	}
	public void setMobilid(String mobilid) {
		this.mobilid = mobilid;
	}
	public String getSigndate() {
		return signdate;
	}
	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}
	public String getTradeid() {
		return tradeid;
	}
	public void setTradeid(String tradeid) {
		this.tradeid = tradeid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCommid() {
		return commid;
	}
	public void setCommid(String commid) {
		this.commid = commid;
	}
	public String getResultcd() {
		return resultcd;
	}
	public void setResultcd(String resultcd) {
		this.resultcd = resultcd;
	}
	public String getResultmsg() {
		return resultmsg;
	}
	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
	}
	public String getCryptyn() {
		return cryptyn;
	}
	public void setCryptyn(String cryptyn) {
		this.cryptyn = cryptyn;
	}
	public String getKeygb() {
		return keygb;
	}
	public void setKeygb(String keygb) {
		this.keygb = keygb;
	}
	public String getSocialno() {
		return socialno;
	}
	public void setSocialno(String socialno) {
		this.socialno = socialno;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getForeigner() {
		return foreigner;
	}
	public void setForeigner(String foreigner) {
		this.foreigner = foreigner;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getDi() {
		return di;
	}
	public void setDi(String di) {
		this.di = di;
	}
	public String getCI_Mode() {
		return CI_Mode;
	}
	public void setCI_Mode(String cI_Mode) {
		CI_Mode = cI_Mode;
	}
	public String getCI_Code() {
		return CI_Code;
	}
	public void setCI_Code(String cI_Code) {
		CI_Code = cI_Code;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getMSTR() {
		return MSTR;
	}
	public void setMSTR(String mSTR) {
		MSTR = mSTR;
	}
	
	@Override
	public String toString() {
		return "MobilUserCertifyResponseVo [svcid=" + svcid + ", mobilid=" + mobilid + ", signdate=" + signdate
				+ ", tradeid=" + tradeid + ", name=" + name + ", name2=" + name2 + ", no=" + no + ", commid=" + commid
				+ ", resultcd=" + resultcd + ", resultmsg=" + resultmsg + ", cryptyn=" + cryptyn + ", keygb=" + keygb
				+ ", socialno=" + socialno + ", sex=" + sex + ", foreigner=" + foreigner + ", ci=" + ci + ", di=" + di
				+ ", CI_Mode=" + CI_Mode + ", CI_Code=" + CI_Code + ", mac=" + mac + ", MSTR=" + MSTR + "]";
	}
	
}
