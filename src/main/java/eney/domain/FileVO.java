package eney.domain;

public class FileVO {
	private Integer file_id;
	private Integer refer_id;
	private String category;
	private String name;
	private String upload_name;
	private String extenstion;
	private Long volume;
	private String userid;
	
	public Integer getFile_id() {
		return file_id;
	}
	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}
	public Integer getRefer_id() {
		return refer_id;
	}
	public void setRefer_id(Integer refer_id) {
		this.refer_id = refer_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUpload_name() {
		return upload_name;
	}
	public void setUpload_name(String upload_name) {
		this.upload_name = upload_name;
	}
	public String getExtenstion() {
		return extenstion;
	}
	public void setExtenstion(String extenstion) {
		this.extenstion = extenstion;
	}
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	
	@Override
	public String toString() {
		return "FileVO [file_id=" + file_id + ", refer_id=" + refer_id + ", category=" + category + ", name=" + name
				+ ", upload_name=" + upload_name + ", extenstion=" + extenstion + ", volume=" + volume + "]";
	}
	public void setUserid(String userid) {
		this.userid = userid;		
	}
	public String getUserid() {
		return userid;
		
	}
}
