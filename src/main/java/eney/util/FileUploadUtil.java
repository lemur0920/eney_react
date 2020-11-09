package eney.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import eney.exception.ExtensionUnqualifiedException;
import eney.property.EneyProperties;
import eney.property.FileProperties;

@Component
public class FileUploadUtil {
	
	@Value("${spring.servlet.multipart.location}")
	private String multipartLocation;
	
	private FileProperties fileProperties;
	
	/* 업로드 파일 검증 부분 */
	public void verifyVolume(long volume) throws MaxUploadSizeExceededException{
		if(volume > fileProperties.getFileMaxVolume())
			throw new MaxUploadSizeExceededException(volume);
	}
	
	public void verifyMmsVolume(long volume) throws MaxUploadSizeExceededException{
		if(volume > fileProperties.getMmsFileMaxVolume())
			throw new MaxUploadSizeExceededException(volume);
	}
	
	public void verifyFileExtenstion(String ext) throws ExtensionUnqualifiedException{
		verifyExtenstion(ext, fileProperties.getFileAvailableExtensions());
	}
	
	public void verifyImgExtenstion(String ext) throws ExtensionUnqualifiedException{
		verifyExtenstion(ext, fileProperties.getImgAvailableExtensions());
	}
	
	public void verifyMmsExtenstion(String ext) throws ExtensionUnqualifiedException{
		verifyExtenstion(ext, fileProperties.getMmsAvailableExtensions());
	}
	
	public void verifySoundExtenstion(String ext) throws ExtensionUnqualifiedException{
		verifyExtenstion(ext, fileProperties.getSoundAvailableExtensions());
	}
	
	/* 업로드 정책 조회 부분 */
	public Path getFileUploadPath(){
//		return Paths.get(fileProperties.getFileLocation());
		return Paths.get(this.multipartLocation);
	}
	
	public int getMaximumVolume() {
		return fileProperties.getFileMaxVolume();
	}
	public String[] getAvailableImgExtenstions() {
		return fileProperties.getImgAvailableExtensions();
	}
	public String[] getAvailableSoundExtenstions() {
		return fileProperties.getSoundAvailableExtensions();
	}
	public String[] getAvailableFileExtenstions() {
		return fileProperties.getFileAvailableExtensions();
	}
	
	@Autowired
	public void setFileProperties(EneyProperties eneyProperties){
		this.fileProperties = eneyProperties.getPortal().getFileProperties();
	}
	
	private void verifyExtenstion(String ext, String[] availableExts) throws ExtensionUnqualifiedException{
		for (String available_extenstion : availableExts) {
			if(ext.equalsIgnoreCase(available_extenstion))
				return;
		}
		throw new ExtensionUnqualifiedException(ext);
	}
}