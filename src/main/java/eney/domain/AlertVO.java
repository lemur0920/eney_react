package eney.domain;

import java.util.List;
import java.util.Map;

public class AlertVO {
	
	/*ALERTS DB COLUMN*/
	private int alert_id;
	private String pusher_id;
	private String puller_id;
	private String type;
	private String menu_name;
	private String date;
	private String description;
	private String redirec_url;
	private int check_yn;
	
	/*ITEM DB COLUMN*/
	private int item_id;
	private String item_code;
	private String item_type;
	private String item_menu_name;
	private String item_description;
	
	/*ITEM CODE FINAL VALUE*/
	public final static String ITEM_CODE_050_EXPIRE_WEEK = "050_EXPIRE_WEEK";
	public final static String ITEM_CODE_050_EXPIRE_DAY = "050_EXPIRE_DAY";
	
	public final static String ITEM_CODE_PAYMENT_SUCCESS = "PAYMENT_SUCCESS";
	
	public final static String ITEM_CODE_SERVICE_PATCHCLL_EXPIRE_WEEK = "SERVICE_PATCHCALL_EXPIRE_WEEK";
	public final static String ITEM_CODE_SERVICE_PATCHCLL_EXPIRE_DAY = "SERVICE_PATCHCALL_EXPIRE_DAY";
	
	public final static String ITEM_CODE_SERVICE_WEB_EXPIRE_WEEK = "SERVICE_WEB_EXPIRE_WEEK";
	public final static String ITEM_CODE_SERVICE_WEB_EXPIRE_DAY = "SERVICE_WEB_EXPIRE_DAY";
	
	public final static String ITEM_CODE_SERVICE_RECORD_EXPIRE_WEEK = "SERVICE_RECORD_EXPIRE_WEEK";
	public final static String ITEM_CODE_SERVICE_RECORD_EXPIRE_DAY = "SERVICE_RECORD_EXPIRE_DAY";
	
	public final static String ITEM_CODE_SERVICE_CALLBACK_EXPIRE_WEEK = "SERVICE_CALLBACK_EXPIRE_WEEK";
	public final static String ITEM_CODE_SERVICE_CALLBACK_EXPIRE_DAY = "SERVICE_CALLBACK_EXPIRE_DAY";
	
	private List<Map<String, Object>> vno_list;
	
	private List<Map<String, Object>> patchcall_service_list;
	
	private List<Map<String, Object>> web_service_list;
	private List<Map<String, Object>> vpn_service_list;
	private List<Map<String, Object>> server_service_list;
	private List<Map<String, Object>> other_service_list;
	private List<Map<String, Object>> callback_service_list;
	private List<Map<String, Object>> record_service_list;
	
	private int affectedRowCnt;
	
	//생성자 item_code로만 가능하게한다.
	public AlertVO(String itemCode050ExpireWeek) {
		this.item_code = itemCode050ExpireWeek;
	}
	public AlertVO(){
		
	}
	public int getAlert_id() {
		return alert_id;
	}
	public void setAlert_id(int alert_id) {
		this.alert_id = alert_id;
	}
	public String getPusher_id() {
		return pusher_id;
	}
	public void setPusher_id(String pusher_id) {
		this.pusher_id = pusher_id;
	}
	public String getPuller_id() {
		return puller_id;
	}
	public void setPuller_id(String puller_id) {
		this.puller_id = puller_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getCheck_yn() {
		return check_yn;
	}
	public void setCheck_yn(int check_yn) {
		this.check_yn = check_yn;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	public String getItem_menu_name() {
		return item_menu_name;
	}
	public void setItem_menu_name(String item_menu_name) {
		this.item_menu_name = item_menu_name;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	@Override
	public String toString() {
		return "AlertVO [alert_id=" + alert_id + ", pusher_id=" + pusher_id
				+ ", puller_id=" + puller_id + ", type=" + type
				+ ", menu_name=" + menu_name + ", date=" + date
				+ ", description=" + description + ", check=" + check_yn + "]";
	}
	public List<Map<String, Object>> getVno_list() {
		return vno_list;
	}
	public void setVno_list(List<Map<String, Object>> vno_list) {
		this.vno_list = vno_list;
	}
	public String getRedirec_url() {
		return redirec_url;
	}
	public void setRedirec_url(String redirec_url) {
		this.redirec_url = redirec_url;
	}
	public int getAffectedRowCnt() {
		return affectedRowCnt;
	}
	public void setAffectedRowCnt(int affectedRowCnt) {
		this.affectedRowCnt = affectedRowCnt;
	}
	public List<Map<String, Object>> getPatchcall_service_list() {
		return patchcall_service_list;
	}
	public void setPatchcall_service_list(List<Map<String, Object>> patchcall_service_list) {
		this.patchcall_service_list = patchcall_service_list;
	}
	public List<Map<String, Object>> getWeb_service_list() {
		return web_service_list;
	}
	public void setWeb_service_list(List<Map<String, Object>> web_service_list) {
		this.web_service_list = web_service_list;
	}
	public List<Map<String, Object>> getVpn_service_list() {
		return vpn_service_list;
	}
	public void setVpn_service_list(List<Map<String, Object>> vpn_service_list) {
		this.vpn_service_list = vpn_service_list;
	}
	public List<Map<String, Object>> getServer_service_list() {
		return server_service_list;
	}
	public void setServer_service_list(List<Map<String, Object>> server_service_list) {
		this.server_service_list = server_service_list;
	}
	public List<Map<String, Object>> getOther_service_list() {
		return other_service_list;
	}
	public void setOther_service_list(List<Map<String, Object>> other_service_list) {
		this.other_service_list = other_service_list;
	}
	public List<Map<String, Object>> getCallback_service_list() {
		return callback_service_list;
	}
	public void setCallback_service_list(List<Map<String, Object>> callback_service_list) {
		this.callback_service_list = callback_service_list;
	}
	public List<Map<String, Object>> getRecord_service_list() {
		return record_service_list;
	}
	public void setRecord_service_list(List<Map<String, Object>> record_service_list) {
		this.record_service_list = record_service_list;
	}
	
}
