package eney.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
//@ConfigurationProperties(prefix="spring.servlet.multipart")
public class FileProperties {

//	@Value("${spring.servlet.multipart.fileLocation}")
	private String fileLocation;
	private Integer fileMaxVolume;
	private Integer mmsFileMaxVolume;
	private String[] fileAvailableExtensions;
	private String[] imgAvailableExtensions;
	private String[] soundAvailableExtensions;
	private String[] mmsAvailableExtensions;
	
	public String getFileLocation() {
		System.out.println("##CCC");
		System.out.println(fileLocation);
		System.out.println("------");
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public Integer getFileMaxVolume() {
		return fileMaxVolume;
	}
	public void setFileMaxVolume(Integer fileMaxVolume) {
		this.fileMaxVolume = fileMaxVolume;
	}
	public String[] getFileAvailableExtensions() {
		return fileAvailableExtensions;
	}
	public void setFileAvailableExtensions(String[] fileAvailableExtensions) {
		this.fileAvailableExtensions = fileAvailableExtensions;
	}
	public String[] getImgAvailableExtensions() {
		return imgAvailableExtensions;
	}
	public void setImgAvailableExtensions(String[] imgAvailableExtensions) {
		this.imgAvailableExtensions = imgAvailableExtensions;
	}
	public String[] getSoundAvailableExtensions() {
		return soundAvailableExtensions;
	}
	public void setSoundAvailableExtensions(String[] soundAvailableExtensions) {
		this.soundAvailableExtensions = soundAvailableExtensions;
	}
	public String[] getMmsAvailableExtensions() {
		return mmsAvailableExtensions;
	}
	public void setMmsAvailableExtensions(String[] mmsAvailableExtensions) {
		this.mmsAvailableExtensions = mmsAvailableExtensions;
	}
	public Integer getMmsFileMaxVolume() {
		return mmsFileMaxVolume;
	}
	public void setMmsFileMaxVolume(Integer mmsFileMaxVolume) {
		this.mmsFileMaxVolume = mmsFileMaxVolume;
	}
	
	
}
