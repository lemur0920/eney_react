package eney.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ColoringUploadVO extends Pagination {
	private int id;
	private int idx;
	private int regid;
	private List<Integer> id_array;
	private String userid;
	private String targetid;
	private String file_name;
	private String description;
	private String upload_date;
	private List<MultipartFile> files;
	private List<FileVO> fileVO_list;
	private String gubun;
	private String file_id;
	private String remark;

	private String type;
	private String ivr;



	private String name;
	private String reg_date;
	private String login_id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List<FileVO> getFileVO_list() {
		return fileVO_list;
	}
	public void setFileVO_list(List<FileVO> fileVO_list) {
		this.fileVO_list = fileVO_list;
	}
	public String getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(String upload_date) {
		this.upload_date = upload_date;
	}
	public List<Integer> getId_array() {
		return id_array;
	}
	public void setId_array(List<Integer> id_array) {
		this.id_array = id_array;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTargetid() {
		return targetid;
	}
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}
	public int getRegid() {
		return regid;
	}
	public void setRegid(int regid) {
		this.regid = regid;
	}

	public String getIvr() {return ivr;}
	public void setIvr(String ivr) {this.ivr = ivr;}
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	@Override
	public String toString() {
		return "ColoringUploadVO{" +
				"id=" + id +
				", idx=" + idx +
				", regid=" + regid +
				", id_array=" + id_array +
				", userid='" + userid + '\'' +
				", targetid='" + targetid + '\'' +
				", file_name='" + file_name + '\'' +
				", description='" + description + '\'' +
				", upload_date='" + upload_date + '\'' +
				", files=" + files +
				", fileVO_list=" + fileVO_list +
				", gubun='" + gubun + '\'' +
				", file_id='" + file_id + '\'' +
				", remark='" + remark + '\'' +
				", type='" + type + '\'' +
				", ivr='" + ivr + '\'' +
				", name='" + name + '\'' +
				", reg_date='" + reg_date + '\'' +
				", login_id='" + login_id + '\'' +
				'}';
	}

}
