package eney.domain;

public class BlockNumberVO extends PageVO{

    private int idx;
    private String userid;
    private String username;
    private String service_type;
    private int service_amount;
    private int service_vat;
    private String service_period;
    private String pay_way;
    private String corporate_address;
    private String ceo_name;
    private String end_date;
    private String generate_yn;
    private PaymentLogVo.PayStatus pay_state;
    private String publish_email;
    private String reg_date;
    private String status;
    private String note;
    private String tel_num;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public int getService_amount() {
        return service_amount;
    }

    public void setService_amount(int service_amount) {
        this.service_amount = service_amount;
    }

    public int getService_vat() {
        return service_vat;
    }

    public void setService_vat(int service_vat) {
        this.service_vat = service_vat;
    }

    public String getService_period() {
        return service_period;
    }

    public void setService_period(String service_period) {
        this.service_period = service_period;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getCorporate_address() {
        return corporate_address;
    }

    public void setCorporate_address(String corporate_address) {
        this.corporate_address = corporate_address;
    }

    public String getCeo_name() {
        return ceo_name;
    }

    public void setCeo_name(String ceo_name) {
        this.ceo_name = ceo_name;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getGenerate_yn() {
        return generate_yn;
    }

    public void setGenerate_yn(String generate_yn) {
        this.generate_yn = generate_yn;
    }

    public PaymentLogVo.PayStatus getPay_state() {
        return pay_state;
    }

    public void setPay_state(PaymentLogVo.PayStatus pay_state) {
        this.pay_state = pay_state;
    }

    public String getPublish_email() {
        return publish_email;
    }

    public void setPublish_email(String publish_email) {
        this.publish_email = publish_email;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTel_num() {
        return tel_num;
    }

    public void setTel_num(String tel_num) {
        this.tel_num = tel_num;
    }


    @Override
    public String toString() {
        return "BlockNumberVO{" +
                "idx=" + idx +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", service_type='" + service_type + '\'' +
                ", service_amount=" + service_amount +
                ", service_vat=" + service_vat +
                ", service_period='" + service_period + '\'' +
                ", pay_way='" + pay_way + '\'' +
                ", corporate_address='" + corporate_address + '\'' +
                ", ceo_name='" + ceo_name + '\'' +
                ", end_date='" + end_date + '\'' +
                ", generate_yn='" + generate_yn + '\'' +
                ", pay_state=" + pay_state +
                ", publish_email='" + publish_email + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", status='" + status + '\'' +
                ", note='" + note + '\'' +
                ", tel_num='" + tel_num + '\'' +
                '}';
    }
}
