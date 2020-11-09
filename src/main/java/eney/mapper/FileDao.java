package eney.mapper;

import java.util.List;

import eney.DatasourceConfig;
import eney.domain.BoardContentVO;
import eney.domain.ColoringUploadVO;
import eney.domain.FileVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.BoardContentVO;
import eney.domain.ColoringUploadVO;
import eney.domain.FileVO;

@Repository
public class FileDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	/**
	 * 파일 업로드(n_fileupload)
	 * @param uploadForm
	 * @return
	 */
	public Integer submitFileVO(Object uploadForm) {
		return (Integer)sqlSession.insert("file.submitFileVO",uploadForm);
	}
	/**
	 * 업데이트 시 기존 파일과 매칭 삭체(수정해야 함)
	 * @param boardContentVO
	 */
	public void updateFile(BoardContentVO boardContentVO) {
		sqlSession.update("file.updateFile",boardContentVO);
	}

	/**
	 * 업로드 이름으로 파일 이름 가져오기
	 * @param fileName
	 */
	public String getOriginalFileNameByUploadName(String fileName) {
		return (String)sqlSession.selectOne("file.getOriginalFileNameByUploadName", fileName);
	}

	/**
	 * 교환기에 컬러링 업로드
	 * @param coloringVO
	 */
	public void insertColorringSourceData(ColoringUploadVO coloringVO) {
		sqlSession.insert("file.insertColorringSourceData", coloringVO);
	}

	public void insertColorringSourceDataSejong(ColoringUploadVO coloringVO) {
		sqlSession.insert("file.insertColorringSourceDataSejong", coloringVO);
	}

	
	/**
	 * 파일 검색
	 * @param selectQuery
	 */
	public FileVO selectFile(FileVO selectQuery){
		return (FileVO) sqlSession.selectOne("file.selectFile", selectQuery);
	}
	
	/**
	 * 파일 리스트 출력 및 검색
	 * @param selectQuery
	 */
	public List<FileVO> selectFileList(FileVO selectQuery){
		return sqlSession.selectList("file.selectFile", selectQuery);
	}

	/**
	 * 교환기에 착신멘트 업로드
	 * @param coloringVO
	 */
	public void insertRcvmentSourceData(ColoringUploadVO coloringVO) {
		sqlSession.insert("file.insertRcvmentSourceData", coloringVO);
	}

	public void insertRcvmentSourceDataSejong(ColoringUploadVO coloringVO) {
		sqlSession.insert("file.insertRcvmentSourceDataSejong", coloringVO);
	}

	/**
	 * invoice가 포함되는 파일 검색
	 * @param invoice_idx
	 * @return
	 */
	public FileVO getFileId(int invoice_idx) {
		return sqlSession.selectOne("file.getFileId",invoice_idx);
	}
	public FileVO getFileVO(String name) {
		return sqlSession.selectOne("file.getFileVO",name);
	}
	public void deleteFile(FileVO fileVO) {
		sqlSession.update("file.deleteFile",fileVO);
	}

	
}
