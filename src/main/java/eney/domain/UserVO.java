package eney.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@ToString
public class UserVO extends PageVO implements UserDetails {

	private static final long serialVersionUID = 444678416318269523L;
	
	public static final String MEMBER_KIND_CORPORATE = "corporate";
	public static final String MEMBER_KIND_PERSONAL = "personal";
	public static final String MEMBER_KIND_AGENT = "agent";

	private int idx;
	private String name;
	private String userid;
	private String birth_day;
	private String password;
	private String password_check;
	private String email_id;
	private String email_domain;
	private String select_domain;
	private String email;
	private String phone_num1;
	private String phone_num2;
	private String phone_num3;
	private String phone_number;
	private boolean sms;
	private boolean email_agree;
	private String account_bank;
	private String account_number;
	private String member_kind;
	private String sex;
	private String corporate_number;
	private String company_name;
	private String company_kind;
	private String reg_date;
	private String di;
	private String ci;
	private String last_login;
	private String password_last_update;
	
	private String select_channel;
	private String channel;
	
	private String select_purpose;
	private String purpose;
	private String address;
	private String address_detail;
	private String post_code;

	@Setter
	@Getter
	private Boolean marketing_agree;

	/* 포인트 충전 시 실시간으로 포인트가 적용되도록 하려고 paymentVO에 있지만, userVO에 추가함. */
	private Date charge_date;
	private int balance_epoint;

	/* Spring Security fields */
	private List<Role> authorities;

	@Getter
	@Setter
	private Boolean auth_patch_call;

	@Getter
	@Setter
	private Boolean auth_patch_dash;

	@Getter
	@Setter
	private Boolean auth_patch_ai;

	@Getter
	@Setter
	private Boolean auth_patch_cloud;

	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	private boolean backdoor_yn = false;

	/* User Info2 */
	private String auth;
	private String type;
	private String epoint;
	private String image_src;

	/**/
	private String select_service;

	/**/
	private String certify_key;

	/* User Info Update */
	private String column;
	private String column_data;
	private String updatedVal;
	private String updatedVal2;

	/**/
	private String search_cate;
	private String search_val;

	/* call cnt */
	private int call_cnt_all;
	private int call_cnt_tda;

	private String view_mode;

	private String phone_number_for_findPw;
//	private String email_for_findPw;
	private String check_number;

	/* identification_code */
	private int iden_code_idx;
	private Date iden_code_date;
	private String identification_code;

	/* admin.users - 회원등록 - 아이디 검색 */
	private String search_cate_id;
	private String search_val_id;

	private boolean pagination_flag;
	
	public static final String TYPE_DROP = "WITHDRAW";

	public UserVO() {

	}

	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEpoint() {
		return epoint;
	}
	public void setEpoint(String epoint) {
		this.epoint = epoint;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getIdx() {
		return idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(String birth_day) {
		this.birth_day = birth_day;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword_check() {
		return password_check;
	}
	public void setPassword_check(String password_check) {
		this.password_check = password_check;
	}
	public String getEmail_address() {
		return this.email_id + "@" + this.email_domain;
	}
	public String getEmail() {
		return this.email;
	}


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressDetail() {
		return address_detail;
	}
	public void setAddressDetail(String address_detail) {
		this.address_detail = address_detail;
	}

	public String getPostCode() {
		return post_code;
	}
	public void setPostCode(String post_code) {
		this.post_code = post_code;
	}

//	public String getEmail_for_findPw(){
//		String email = getEmail();
//		int idx = email.indexOf("@");
//		String email_id = email.substring(0, idx);
//		String email_domain = email.substring(idx + 1, email.length());
//		String ret = email_id.substring(0, 2);
//		ret += email_id.substring(2, email_id.length()).replaceAll(".", "*");
//		ret += "@";
//		int idx2 = email_domain.lastIndexOf(".");
//		ret += email_domain.substring(0, idx2).replaceAll(".", "*");
//		ret += ".";
//		ret += email_domain.substring(idx2+1, email_domain.length());
//		return ret;
//	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_num() {
		return this.phone_num1 + "-" + this.phone_num2 + "-" + this.phone_num3;
	}
	public String getPhone_number() {
		if(this.phone_number == null)
			return this.phone_number;
		int len = this.phone_number.length();
		if(len < 10)
			return null;
		else if(len<=11)
			return this.phone_number.substring(0, 3) + "-" + this.phone_number.substring(3, len-4) + "-" + this.phone_number.substring(len-4,len);
		else	
			return this.phone_number;
	}
//	public String getPhone_number_for_findPw(){
//		String phone_number = getPhone_number();
//		int len = phone_number.length();
//
//		if(len==13)
//			return phone_number.substring(0, 5) + "***-***" + phone_number.substring(len-1, len);
//		else if(len==12)
//			return phone_number.substring(0, 5) + "**-***" + phone_number.substring(len-1, len);
//		else
//			return null;
//
//	}
	
	public String getCheck_number() {
		return check_number;
	}
	public void setCheck_number(String check_number) {
		this.check_number = check_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getAccount_bank() {
		return account_bank;
	}
	public void setAccount_bank(String account_bank) {
		this.account_bank = account_bank;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMember_kind() {
		return member_kind;
	}
	public void setMember_kind(String member_kind) {
		this.member_kind = member_kind;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCorporate_number() {
		if(corporate_number!=null){
			if(corporate_number.equals("--"))
				corporate_number = null;
		}
		return corporate_number;
	}
	public void setCorporate_number(String corporate_number) {
		this.corporate_number = corporate_number;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_kind() {
		return company_kind;
	}
	public void setCompany_kind(String company_kind) {
		this.company_kind = company_kind;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getLast_login() {
		return last_login;
	}
	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}

	public String getPassword_last_update() {
		return password_last_update;
	}
	public void setPassword_last_update(String password_last_update) {
		this.password_last_update = password_last_update;
	}

	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getEmail_domain() {
		return email_domain;
	}
	public void setEmail_domain(String email_domain) {
		this.email_domain = email_domain;
	}
	public String getPhone_num1() {
		return phone_num1;
	}
	public void setPhone_num1(String phone_num1) {
		this.phone_num1 = phone_num1;
	}
	public String getPhone_num2() {
		return phone_num2;
	}
	public void setPhone_num2(String phone_num2) {
		this.phone_num2 = phone_num2;
	}
	public String getPhone_num3() {
		return phone_num3;
	}
	public void setPhone_num3(String phone_num3) {
		this.phone_num3 = phone_num3;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		//기본 유저 
		authorities.add(new SimpleGrantedAuthority("ROLE_"+this.getAuth()));

		if(this.getAuth_patch_call() != null && this.getAuth_patch_call()){
			authorities.add(new SimpleGrantedAuthority("ROLE_PATCH_CALL"));
		}
		if(this.getAuth_patch_dash() != null && this.getAuth_patch_dash()){
			authorities.add(new SimpleGrantedAuthority("ROLE_PATCH_DASH"));
		}
		if(this.getAuth_patch_ai() != null && this.getAuth_patch_ai()){
			authorities.add(new SimpleGrantedAuthority("ROLE_PATCH_AI"));
		}

		if(this.getAuth_patch_cloud() != null && this.getAuth_patch_cloud()){
			authorities.add(new SimpleGrantedAuthority("ROLE_PATCH_CLOUD"));
		}
         
        return authorities;
	}
	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public boolean isEnabled() {
		return this.enabled;
	}
	public String getUsername() {
		return userid;
	}
	public String getSelect_domain() {
		return select_domain;
	}
	public void setSelect_domain(String select_domain) {
		this.select_domain = select_domain;
	}
	public boolean isBackdoor_yn() {
		return backdoor_yn;
	}
	public void setBackdoor_yn(boolean backdoor_yn) {
		this.backdoor_yn = backdoor_yn;
	}
	public String getCertify_key() {
		return certify_key;
	}
	public void setCertify_key(String certify_key) {
		this.certify_key = certify_key;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getUpdatedVal() {
		return updatedVal;
	}
	public void setUpdatedVal(String updatedVal) {
		this.updatedVal = updatedVal;
	}
	public String getUpdatedVal2() {
		return updatedVal2;
	}
	public void setUpdatedVal2(String updatedVal2) {
		this.updatedVal2 = updatedVal2;
	}
	public String getColumn_data() {
		return column_data;
	}
	public void setColumn_data(String column_data) {
		this.column_data = column_data;
	}
	public String getSearch_cate() {
		return search_cate;
	}
	public void setSearch_cate(String search_cate) {
		this.search_cate = search_cate;
	}
	public String getSearch_val() {
		return search_val;
	}
	public void setSearch_val(String search_val) {
		this.search_val = search_val;
	}
	public String getView_mode() {
		return view_mode;
	}
	public void setView_mode(String view_mode) {
		this.view_mode = view_mode;
	}
	public boolean isSms() {
		return sms;
	}
	public void setSms(boolean sms) {
		this.sms = sms;
	}
	public boolean isEmail_agree() {
		return email_agree;
	}
	public void setEmail_agree(boolean email_agree) {
		this.email_agree = email_agree;
	}
	public int getCall_cnt_all() {
		return call_cnt_all;
	}
	public void setCall_cnt_all(int call_cnt_all) {
		this.call_cnt_all = call_cnt_all;
	}
	public int getCall_cnt_tda() {
		return call_cnt_tda;
	}
	public void setCall_cnt_tda(int call_cnt_tda) {
		this.call_cnt_tda = call_cnt_tda;
	}
	public String getImage_src() {
		return image_src;
	}
	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}
	public String getSelect_service() {
		return select_service;
	}
	public void setSelect_service(String select_service) {
		this.select_service = select_service;
	}
	public void setPhone_number_for_findPw(String phone_number_for_findPw) {
		this.phone_number_for_findPw = phone_number_for_findPw;
	}
	public int getIdentification_code_idx() {
		return iden_code_idx;
	}
	public void setIdentification_code_idx(int identification_code_idx) {
		this.iden_code_idx = identification_code_idx;
	}
	public Date getCode_date() {
		return iden_code_date;
	}
	public void setCode_date(Date code_date) {
		this.iden_code_date = code_date;
	}
	public String getIdentification_code() {
		return identification_code;
	}
	public void setIdentification_code(String identification_code) {
		this.identification_code = identification_code;
	}
	public int getIden_code_idx() {
		return iden_code_idx;
	}
	public void setIden_code_idx(int iden_code_idx) {
		this.iden_code_idx = iden_code_idx;
	}
	public Date getIden_code_date() {
		return iden_code_date;
	}
	public void setIden_code_date(Date iden_code_date) {
		this.iden_code_date = iden_code_date;
	}
	
	public Date getCharge_date() {
		return charge_date;
	}
	public void setCharge_date(Date charge_date) {
		this.charge_date = charge_date;
	}
	public int getBalance_epoint() {
		return balance_epoint;
	}
	public void setBalance_epoint(int balance_epoint) {
		this.balance_epoint = balance_epoint;
	}
	public String getSearch_cate_id() {
		return search_cate_id;
	}
	public void setSearch_cate_id(String search_cate_id) {
		this.search_cate_id = search_cate_id;
	}
	public String getSearch_val_id() {
		return search_val_id;
	}
	public void setSearch_val_id(String search_val_id) {
		this.search_val_id = search_val_id;
	}
	public boolean isPagination_flag() {
		return pagination_flag;
	}
	public void setPagination_flag(boolean pagination_flag) {
		this.pagination_flag = pagination_flag;
	}
	public String getDi() {
		return di;
	}
	public void setDi(String di) {
		this.di = di;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}	

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public String getSelect_channel() {
		return select_channel;
	}

	public void setSelect_channel(String select_channel) {
		this.select_channel = select_channel;
	}

	public String getSelect_purpose() {
		return select_purpose;
	}

	public void setSelect_purpose(String select_purpose) {
		this.select_purpose = select_purpose;
	}

//	@Override
//	public String toString() {
//		return "UserVO [idx=" + idx + ", name=" + name + ", userid=" + userid + ", birth_day=" + birth_day
//				+ ", password=" + password + ", password_check=" + password_check + ", email_id=" + email_id
//				+ ", email_domain=" + email_domain + ", select_domain=" + select_domain + ", email=" + email
//				+ ", phone_num1=" + phone_num1 + ", phone_num2=" + phone_num2 + ", phone_num3=" + phone_num3
//				+ ", phone_number=" + phone_number + ", sms=" + sms + ", email_agree=" + email_agree + ", account_bank="
//				+ account_bank + ", account_number=" + account_number + ", member_kind=" + member_kind + ", sex=" + sex
//				+ ", corporate_number=" + corporate_number + ", company_name=" + company_name + ", reg_date=" + reg_date
//				+ ", di=" + di + ", ci=" + ci + ", last_login=" + last_login + ", channel=" + channel + ", purpose="
//				+ purpose + ", charge_date=" + charge_date + ", balance_epoint=" + balance_epoint + ", authorities="
//				+ authorities + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
//				+ ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + ", backdoor_yn="
//				+ backdoor_yn + ", auth=" + auth + ", type=" + type + ", epoint=" + epoint + ", image_src=" + image_src
//				+ ", select_service=" + select_service + ", certify_key=" + certify_key + ", column=" + column
//				+ ", column_data=" + column_data + ", updatedVal=" + updatedVal + ", updatedVal2=" + updatedVal2
//				+ ", search_cate=" + search_cate + ", search_val=" + search_val + ", call_cnt_all=" + call_cnt_all
//				+ ", call_cnt_tda=" + call_cnt_tda + ", view_mode=" + view_mode + ", phone_number_for_findPw="
//				+ phone_number_for_findPw  + ", check_number=" + check_number
//				+ ", iden_code_idx=" + iden_code_idx + ", iden_code_date=" + iden_code_date + ", identification_code="
//				+ identification_code + ", search_cate_id=" + search_cate_id + ", search_val_id=" + search_val_id
//				+ ", pagination_flag=" + pagination_flag + "]";
//	}
	
}
