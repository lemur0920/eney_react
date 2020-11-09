package eney.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eney.domain.BoardContentVO;
import eney.domain.ColoringUploadVO;
import eney.domain.FileVO;
import eney.exception.ExtensionUnqualifiedException;
import eney.util.Base32HexUtil;
import eney.util.FileUploadUtil;
import eney.util.SFTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import eney.mapper.FileDao;

@Service
public class FileService {
	
	public static final String UPLOAD_CATE_COLORING = "COLORING";
	public static final String UPLOAD_CATE_COLORING2 = "COLORING2";
	public static final String UPLOAD_CATE_COLORING_SEJONG = "COLORING_SEJONG";
	public static final String UPLOAD_CATE_RCVMENT_SKB = "RCVMENT_SKB";
	public static final String UPLOAD_CATE_RCVMENT_SEJONG = "RCVMENT_SEJONG";
	public static final String UPLOAD_CATE_ADPOWER_EVENT = "ADPOWER_EVENT";
	public static final String UPLOAD_CATE_ADVER_INFO = "ADVER_INFO";
	public static final String UPLOAD_CATE_CONTENT_IMG = "CONTENT_IMG";
	public static final String UPLOAD_CATE_MMS = "MMS"; 
	public static final String UPLOAD_CATE_CORPORATE_WEB = "CORPORATE_WEB";
	public static final String UPLOAD_CATE_CORPORATE_OTHER = "CORPORATE_OTHER";
	public static final String UPLOAD_CATE_CORPORATE_PATCHCALL = "CORPORATE_PATCHCALL";
	public static final String UPLOAD_CATE_CORPORATE_CALLBACK = "CORPORATE_CALLBACK";
	public static final String UPLOAD_CATE_CORPORATE_RECORD = "CORPORATE_RECORD";
	public static final String UPLOAD_CATE_GA_SERVICE_KEY = "BI_SERVICE_KEY";
	public static final String UPLOAD_CATE_TUMBNAIL = "TUMBNAIL";
	public static final String UPLOAD_CATE_LICENSE = "LICENSE";
	public static final String X_TECH = "X_TECH";


	private static final Logger logger = LoggerFactory.getLogger(Service.class);
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileUploadUtil fileUploadUtil;
	
	public Integer submitFileVO(Object uploadForm) {
		return fileDao.submitFileVO(uploadForm);
	}

	/**
	 * 파일 업로드 로직
	 * @param files 
	 * @param category 파일 분류
	 * @param indexNum 컨텐츠 IDX
	 * @param request
	 * @param targetid
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> coloringUpload(List<MultipartFile> files, ColoringUploadVO uploadForm, String category, int indexNum, HttpServletRequest request, String... targetid) throws Exception {
		List<FileVO> fileVOList = new ArrayList<FileVO>();
		if (files!=null && files.get(0) != null && files.get(0).getSize() > 0) {
			for (MultipartFile multipartFile : files) {
				FileVO fileVO = new FileVO();
				String fileName = multipartFile.getOriginalFilename();
				Path fileUploadPath = null;
				String uploadName = getUploadFileName(multipartFile, category, indexNum);

				
				fileVO.setRefer_id(indexNum);
				fileVO.setName(fileName);
				fileVO.setCategory(category);
				fileVO.setExtenstion(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase());
				fileVO.setVolume(multipartFile.getSize());
				fileVO.setUpload_name(uploadName);
				
				if(multipartFile.isEmpty()){
					logger.info("[파일 업로드] 실패 - 업로드 파일이 비여있는 파일입니다."
							+ "( fileVO: " + fileVO
							+ ", fileInfo: " + multipartFile
							+ ", IP: " + request.getRemoteAddr() + ")");
					break;
				}
				
				try{
					checkVerifyExtenstion(fileVO.getExtenstion(), fileVO.getVolume(), category);
				} catch(ExtensionUnqualifiedException e) {
					logger.info("[파일 업로드] 실패 - 허용되지 않는 확장자입니다."
							+ "(Extenstion: " + fileVO.getExtenstion()
							+ ", IP: " + request.getRemoteAddr() + ")");
					
					break;
				}
				
				/* 경로 지정 */
				fileUploadPath = getUploadPath(category).resolve(fileVO.getUpload_name());
				
				
				/* 업로드 파일 저장 */
				try{
					Files.copy(multipartFile.getInputStream(), fileUploadPath);
				} catch(IOException e) {
					logger.warn("[파일 업로드] 실패 - I/O 예외, 파일을 저장할 수 없습니다."
								+ "(multipartFile: " + multipartFile
								+ ", e: " + e.getMessage()
								+ ", IP: " + request.getRemoteAddr() + ")");
					throw e;
				} catch (SecurityException e) {
					logger.warn("[파일 업로드] 실패 - 보안 예외, 파일을 저장할 수 없습니다."
							+ "(multipartFile: " + multipartFile
							+ ", e: " + e.getMessage()
							+ ", IP: " + request.getRemoteAddr() + ")");
					throw e;
				}
				
				if(UPLOAD_CATE_COLORING.equals(category) || UPLOAD_CATE_COLORING2.equals(category) || UPLOAD_CATE_RCVMENT_SKB.equals(category)){
					SFTPUtil sftpUtil = new SFTPUtil();
					fileUploadPath.toAbsolutePath();
					sftpUtil.upload(fileUploadPath.getFileName().toString(),fileUploadPath, "/home/onse/BIZ_CALL/ment", "root", "58.229.254.247", 22, "root^1234");

				}else if(UPLOAD_CATE_COLORING_SEJONG.equals(category) || UPLOAD_CATE_RCVMENT_SEJONG.equals(category)){
					SFTPUtil sftpUtil = new SFTPUtil();
					fileUploadPath.toAbsolutePath();
					sftpUtil.upload(fileUploadPath.getFileName().toString(),fileUploadPath, "/home/eney/ment", "root", "210.103.187.22", 22, "root^1234");
				}

				//교환기 등록
				submitIvr(uploadForm, uploadName, category);

				fileVOList.add(fileVO);
				logger.info("[파일 업로드] 완료"
						+ "(fileVO: " + fileVO
						+ ", IP: " + request.getRemoteAddr() + ")");
			}
			//n_file_upload
			Integer idx = submitFileVO(fileVOList);

			return fileVOList;
			
		}else{
			return null;
		}
	}

	public void submitIvr(ColoringUploadVO uploadForm, String fileName, String category){

		ColoringUploadVO coloringVO = new ColoringUploadVO();

		coloringVO.setFile_name(fileName);
		coloringVO.setLogin_id(uploadForm.getUserid());

		if(UPLOAD_CATE_COLORING.equals(category) || UPLOAD_CATE_COLORING2.equals(category)){
			coloringVO.setName("COLORING");
			fileDao.insertColorringSourceData(coloringVO);
		}else if(UPLOAD_CATE_RCVMENT_SKB.equals(category)){
			coloringVO.setName("RCVMENT");
			fileDao.insertRcvmentSourceData(coloringVO);
		}else if(UPLOAD_CATE_COLORING_SEJONG.equals(category)){
			coloringVO.setName("COLORING");
			fileDao.insertColorringSourceDataSejong(coloringVO);
		}else if(UPLOAD_CATE_RCVMENT_SEJONG.equals(category)){
			coloringVO.setName("RCVMENT");
			fileDao.insertRcvmentSourceDataSejong(coloringVO);
		}

	}

	public List<FileVO> processUpload(List<MultipartFile> files, String category, int indexNum, HttpServletRequest request, String... targetid) throws Exception {
		List<FileVO> fileVOList = new ArrayList<FileVO>();
		if (files!=null && files.get(0) != null && files.get(0).getSize() > 0) {
			for (MultipartFile multipartFile : files) {
				FileVO fileVO = new FileVO();
				String fileName = multipartFile.getOriginalFilename();
				Path fileUploadPath = null;

				fileVO.setRefer_id(indexNum);
				fileVO.setName(fileName);
				fileVO.setCategory(category);
				fileVO.setExtenstion(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase());
				fileVO.setVolume(multipartFile.getSize());
				fileVO.setUpload_name(getUploadFileName(multipartFile, category, indexNum));
				if(category.equals(X_TECH)){
					fileVO.setUpload_name(targetid[0]);
				}


				if(multipartFile.isEmpty()){
					logger.info("[파일 업로드] 실패 - 업로드 파일이 비여있는 파일입니다."
							+ "( fileVO: " + fileVO
							+ ", fileInfo: " + multipartFile
							+ ", IP: " + request.getRemoteAddr() + ")");
					break;
				}

				try{
					checkVerifyExtenstion(fileVO.getExtenstion(), fileVO.getVolume(), category);
				} catch(ExtensionUnqualifiedException e) {
					logger.info("[파일 업로드] 실패 - 허용되지 않는 확장자입니다."
							+ "(Extenstion: " + fileVO.getExtenstion()
							+ ", IP: " + request.getRemoteAddr() + ")");

					break;
				}

				/* 경로 지정 */
				fileUploadPath = getUploadPath(category).resolve(fileVO.getUpload_name()+"."+fileVO.getExtenstion());


				/* 업로드 파일 저장 */
				try{
					Files.copy(multipartFile.getInputStream(), fileUploadPath);
				} catch(IOException e) {
					System.out.println(fileUploadPath);
					logger.warn("[파일 업로드] 실패 - I/O 예외, 파일을 저장할 수 없습니다."
							+ "(multipartFile: " + multipartFile
							+ ", e: " + e.getMessage()
							+ ", IP: " + request.getRemoteAddr() + ")");
					throw e;
				} catch (SecurityException e) {
					logger.warn("[파일 업로드] 실패 - 보안 예외, 파일을 저장할 수 없습니다."
							+ "(multipartFile: " + multipartFile
							+ ", e: " + e.getMessage()
							+ ", IP: " + request.getRemoteAddr() + ")");
					throw e;
				}


				if(UPLOAD_CATE_MMS.equals(category)){
					SFTPUtil sftpUtil = new SFTPUtil();
					fileUploadPath.toAbsolutePath();
					sftpUtil.upload(fileUploadPath.getFileName().toString(),fileUploadPath, "/usr/local/upload/mms", "root", "210.103.187.15", 22, "!eneyMain!@#JJH");
				}

				fileVOList.add(fileVO);
				logger.info("[파일 업로드] 완료"
						+ "(fileVO: " + fileVO
						+ ", IP: " + request.getRemoteAddr() + ")");
			}
			submitFileVO(fileVOList);

			return fileVOList;

		}else{
			return null;
		}
	}

	public ResponseEntity<byte[]> imageLoad(String category, String fileName) throws Exception {

//		System.out.println(fileUploadUtil.getFileUploadPath().resolve(filePath).toString());
//		System.out.println(fileUploadUtil.getFileUploadPath().resolve(filePath).toString()+"/" + fileName);

		File img = new File(fileUploadUtil.getFileUploadPath().resolve(category).toString()+"/" + fileName);
		return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));

	}

	public String tumbnailUpload(MultipartFile file,String category) throws Exception {

		FileVO fileVO = new FileVO();
		String fileName = file.getOriginalFilename();
//		Path fileUploadPath = null;

//		fileVO.setRefer_id(indexNum);
		fileVO.setName(fileName);
		fileVO.setCategory(category);
		fileVO.setExtenstion(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase());
		fileVO.setVolume(file.getSize());
		fileVO.setUpload_name(getUploadFileName(file, category, 0));

		Path fileUploadPath = getUploadPath(category).resolve(fileVO.getUpload_name()+"."+fileVO.getExtenstion());

		try{
			Files.copy(file.getInputStream(), fileUploadPath);
		} catch(IOException e) {
			System.out.println(fileUploadPath);
			logger.warn("[파일 업로드] 실패 - I/O 예외, 파일을 저장할 수 없습니다."
					+ "(multipartFile: " + file
					+ ", e: " + e.getMessage());
			throw e;
		} catch (SecurityException e) {
			logger.warn("[파일 업로드] 실패 - 보안 예외, 파일을 저장할 수 없습니다."
					+ "(multipartFile: " + file
					+ ", e: " + e.getMessage());
			throw e;
		}

		return fileVO.getUpload_name();
	}


	public String mmsImageUpload(MultipartFile file,String category) throws Exception {

		FileVO fileVO = new FileVO();
		String fileName = file.getOriginalFilename();
//		Path fileUploadPath = null;

//		fileVO.setRefer_id(indexNum);
		fileVO.setName(fileName);
		fileVO.setCategory(category);
		fileVO.setExtenstion(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase());
		fileVO.setVolume(file.getSize());
		fileVO.setUpload_name(getUploadFileName(file, category, 0));

		Path fileUploadPath = getUploadPath(category).resolve(fileVO.getUpload_name()+"."+fileVO.getExtenstion());
		System.out.println("fileUploadPath :: " + fileUploadPath);

		try{
			Files.copy(file.getInputStream(), fileUploadPath);

			SFTPUtil sftpUtil = new SFTPUtil();
			fileUploadPath.toAbsolutePath();
			System.out.println("sftp upload start");
			System.out.println(fileUploadPath.getFileName().toString());
			System.out.println(fileUploadPath);

//			sftpUtil.upload(fileUploadPath.getFileName().toString(),fileUploadPath, "/upload/mms", "root", "210.103.188.124", 22, "eney12!@");
			sftpUtil.upload(fileUploadPath.getFileName().toString(),fileUploadPath, "/upload/mms", "root", "210.103.187.15", 22, "!eneyMain!@#JJH");


		} catch(IOException e) {
			System.out.println(fileUploadPath);
			logger.warn("[파일 업로드] 실패 - I/O 예외, 파일을 저장할 수 없습니다."
					+ "(multipartFile: " + file
					+ ", e: " + e.getMessage());
			throw e;
		} catch (SecurityException e) {
			logger.warn("[파일 업로드] 실패 - 보안 예외, 파일을 저장할 수 없습니다."
					+ "(multipartFile: " + file
					+ ", e: " + e.getMessage());
			throw e;
		}

//		return fileVO.getUpload_name();
		return fileUploadPath.toString();
	}

	/**
	 * FileVo 조회 (file_id를 가지고 조회)
	 * @param file_id file_id
	 * @return fileVo 내용
	 */
	public FileVO getFileVoInfo(int file_id){
		FileVO selectQuery = new FileVO();
		
		selectQuery.setFile_id(file_id);
		
		FileVO fileVoInfo = fileDao.selectFile(selectQuery);
		
		return fileVoInfo;
	}
	
	/**
	 * FileVo 조회 (category와 refe_id를 가지고 조회)
	 * @param category 카테고리
//	 * @param refeId 대상 id
	 * @return fileVo 내용
	 */
	public FileVO getFileVoInfo(String category, int refer_id){
		FileVO selectQuery = new FileVO();
		
		selectQuery.setCategory(category);
		selectQuery.setRefer_id(refer_id);
		
		FileVO fileVoInfo = fileDao.selectFile(selectQuery);
		
		return fileVoInfo;
	}
	
	/**
	 * FileVO 내용을 기준으로 File 객체 생성
	 * @param fileVoInfo FileVO
	 * @return 생성된 File 객체
	 * @throws MalformedURLException 
	 */
	public Resource getFile(FileVO fileVoInfo){
		try{
			return new UrlResource(getUploadPath(fileVoInfo.getCategory()).resolve(fileVoInfo.getUpload_name()).toUri());
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	/**
	 * 업로드 파일 검사 (확장자, 크기 검사)
	 * @param extenstion 확장자
	 * @param volume 파일 크기
	 * @param category 카테고리
	 * @throws ExtensionUnqualifiedException 검사를 통과하지 못했을 경우 발생하는 예외
	 */
	public void checkVerifyExtenstion(String extenstion, long volume, String category) throws ExtensionUnqualifiedException{
		if(UPLOAD_CATE_ADPOWER_EVENT.equals(category) || UPLOAD_CATE_ADVER_INFO.equals(category) 
				|| UPLOAD_CATE_CONTENT_IMG.equals(category)){
			fileUploadUtil.verifyImgExtenstion(extenstion);
			fileUploadUtil.verifyVolume(volume);
		}else if(UPLOAD_CATE_COLORING.equals(category)
				|| UPLOAD_CATE_COLORING2.equals(category)
				|| UPLOAD_CATE_COLORING_SEJONG.equals(category)
				|| UPLOAD_CATE_RCVMENT_SKB.equals(category)
				|| UPLOAD_CATE_RCVMENT_SEJONG.equals(category)
				){
			fileUploadUtil.verifySoundExtenstion(extenstion);
			fileUploadUtil.verifyVolume(volume);
		}else if(UPLOAD_CATE_MMS.equals(category)){
			fileUploadUtil.verifyMmsExtenstion(extenstion);
			fileUploadUtil.verifyMmsVolume(volume);
		}else if(X_TECH.equals(category)){
			System.out.println("x-tech");
		}else {
			fileUploadUtil.verifyFileExtenstion(extenstion);
			fileUploadUtil.verifyVolume(volume);
		}
	}
	
	/**
	 * 파일 업로드 경로를 알려주는 함수 (경로에 폴더가 없는 경우 해당 경로에 폴더를 생성)
	 * @param category 카테고리
	 * @return 업로드 파일 저장 경로
	 */
	public Path getUploadPath(String category){
		Path path = fileUploadUtil.getFileUploadPath();		//fileUploadUtil.getFileUploadPath();

		
		if(UPLOAD_CATE_CONTENT_IMG.equals(category)){
			path = path.resolve("image");
		} else if(UPLOAD_CATE_COLORING.equals(category)
				|| UPLOAD_CATE_COLORING2.equals(category)
				|| UPLOAD_CATE_COLORING_SEJONG.equals(category)
				|| UPLOAD_CATE_RCVMENT_SKB.equals(category)
				|| UPLOAD_CATE_RCVMENT_SEJONG.equals(category)
				){
			path = path.resolve("coloring");
		} else if(UPLOAD_CATE_MMS.equals(category)){
			path = path.resolve("mms");
		} else if(UPLOAD_CATE_CORPORATE_CALLBACK.equals(category) || UPLOAD_CATE_CORPORATE_PATCHCALL.equals(category)
				|| UPLOAD_CATE_CORPORATE_WEB.equals(category) || UPLOAD_CATE_CORPORATE_RECORD.equals(category)
				|| UPLOAD_CATE_CORPORATE_OTHER.equals(category)){
			path = path.resolve("corporate");
		}else if(UPLOAD_CATE_GA_SERVICE_KEY.equals(category)){
			path = path.resolve("bi");
		}else if (UPLOAD_CATE_TUMBNAIL.equals(category)){
			path = path.resolve("tumbnail");
		} else if (UPLOAD_CATE_LICENSE.equals(category)) {
			path = path.resolve("LICENSE");

		}
		
		return path;
	}
	
	/**
	 * 파일 업로드 파일명을 지정하는 함수
	 * @param file 파일 정보
	 * @param category 업로드 카테고리
	 * @param indexNum 대상 IDX
	 * @return 저장될 파일명
	 */
	public String getUploadFileName(MultipartFile file, String category, int indexNum){
		String uploadName = "";
		if(UPLOAD_CATE_COLORING.equals(category)){
			uploadName = "3" + Base32HexUtil.encode(indexNum) + ".wav";
		} else if(UPLOAD_CATE_COLORING2.equals(category)){
			uploadName = "4" + Base32HexUtil.encode(indexNum) + ".wav";
		}  else if(UPLOAD_CATE_COLORING_SEJONG.equals(category)){
			uploadName = "5" + Base32HexUtil.encode(indexNum) + ".wav";
		}  else if(UPLOAD_CATE_RCVMENT_SEJONG.equals(category)){
			uploadName = "6" + Base32HexUtil.encode(indexNum) + ".wav";
		}  else if(UPLOAD_CATE_RCVMENT_SKB.equals(category)){
			uploadName = "7" + Base32HexUtil.encode(indexNum) + ".wav";
		} else {
			uploadName = category +  Base32HexUtil.encode(indexNum) + UUID.randomUUID().toString();
		}
		uploadName = uploadName.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "");
		
		return uploadName;
	}
	/**
	 * 업데이트 시 기존 파일과 매칭 삭체(수정해야 함)
	 * @param boardContentVO
	 */
	public void updateFile(BoardContentVO boardContentVO) {
		fileDao.updateFile(boardContentVO);
	}
	
	public List<FileVO> selectFileList(String category, Integer refer_id){
		FileVO selectQuery = new FileVO();
		selectQuery.setCategory(category);
		selectQuery.setRefer_id(refer_id);
		
		return fileDao.selectFileList(selectQuery);
	}

	public FileVO getFileVO(String name) {
		return fileDao.getFileVO(name);
	}

	public void deleteFile(FileVO fileVO) {
		fileDao.deleteFile(fileVO);
	}
	
}
