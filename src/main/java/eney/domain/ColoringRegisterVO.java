package eney.domain;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class ColoringRegisterVO extends PageVO {
	@Getter
	@Setter
	private int rownum;

	private int id;
	private String userid;
	private String script;
	private String voice_tone;
	private String bgm;
	private String write_date;
	private String status;
	private List<Map<String,String>> status_list;
	private List<MultipartFile> files;
	private List<FileVO> fileVO_list;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getVoice_tone() {
		return voice_tone;
	}
	public void setVoice_tone(String voice_tone) {
		this.voice_tone = voice_tone;
	}
	public String getBgm() {
		return bgm;
	}
	public void setBgm(String bgm) {
		this.bgm = bgm;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "ColoringRegisterVO [id=" + id + ", userid=" + userid
				+ ", script=" + script + ", voice_tone=" + voice_tone
				+ ", bgm=" + bgm + ", write_date=" + write_date + ", status="
				+ status + "]";
	}
	public List<Map<String,String>> getStatus_list() {
		return status_list;
	}
	public void setStatus_list(List<Map<String,String>> status_list) {
		this.status_list = status_list;
	}
	public List<FileVO> getFileVO_list() {
		return fileVO_list;
	}
	public void setFileVO_list(List<FileVO> fileVO_list) {
		this.fileVO_list = fileVO_list;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	
}
