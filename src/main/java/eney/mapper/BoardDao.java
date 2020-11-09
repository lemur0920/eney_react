package eney.mapper;

import java.util.HashMap;
import java.util.List;

import eney.DatasourceConfig;
import eney.domain.BoardConfigVO;
import eney.domain.BoardContentVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.BoardConfigVO;
import eney.domain.BoardContentVO;

@Repository
public class BoardDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;

	/**
	 * 게시판 게시글 개수 출력
	 * @param boardContentList
	 * @return 게시글 개수 (int)
	 */
	public int getBoardContentListCnt(BoardContentVO boardContentList) {
		return (Integer)sqlSession.selectOne("board.getBoardContentListCnt", boardContentList);
	}
	

	
	/**
	 * 게시판 게시글 삭제
	 * @param boardContentVO
	 */
	public void delete(BoardContentVO boardContentVO) {
		sqlSession.delete("board.deleteBoardContent", boardContentVO);
	}

	/**
	 * 게시글 상세 정보 출력
	 * @param boardContentVO content_id 기준
	 * @return 게시글 정보
	 */
	public BoardContentVO get(BoardContentVO boardContentVO) {
		return sqlSession.selectOne("board.getContent", boardContentVO);
	}
	
	/**
	 * 게시판 게시글 등록
	 * @param boardContentVO
	 */
	public int insertBoardContent(BoardContentVO boardContentVO) {
		return sqlSession.insert("board.insertBoardContent", boardContentVO);
	}
	

	/**
	 * 게시판 게시글 수정
	 * @param boardContentVO
	 */
	public void updateBoardContent(BoardContentVO boardContentVO) {
		sqlSession.update("board.updateBoardContent", boardContentVO);
	}
	
	/**
	 * 게시판 카테고리 검색
	 * @param board_name (ex notice(공지사항),recruit(채용공고),help(도움말) 등등 담아져있는 변수명
	 * @return 해당 게시판 카테고리( 객체 형태 BoardConfigVO)
	 */
	public BoardConfigVO getBoardConfigByPath(String board_name) {
		return (BoardConfigVO) sqlSession.selectOne("board.getBoardConfigByPath", board_name);
	}

	public void insertBoardContentTobeDeleted(BoardContentVO boardContentVO) {
		sqlSession.insert("board.boardContentVO",boardContentVO);		
	}
	
	/**
	 * 공지사항 게시판 출력
	 * @param boardContentVO
	 * @return
	 */
	public List<BoardContentVO> getNoticeBoardList(BoardContentVO boardContentVO) {
		return sqlSession.selectList("board.getNoticeBoardList",boardContentVO);
	}
	
	/**
	 * 조회수 업데이트
	 * @param int
	 * @return
	 */
	public int updateHits(int contentId) {
		return sqlSession.update("board.updateHits", contentId);
	}

	public BoardContentVO getPreContent(BoardContentVO boardContentVO) {
		
		return sqlSession.selectOne("board.getPreContent", boardContentVO);
	}

	public BoardContentVO getNextContent(BoardContentVO boardContentVO) {
		return sqlSession.selectOne("board.getNextContent", boardContentVO);
	}

	public List<BoardContentVO> getBoardContentListById(BoardContentVO boardContentVO) {
		return sqlSession.selectList("board.getBoardContentListById",boardContentVO);
	}

	public int getTotalContentCountById(int board_id) {
		return (Integer) sqlSession.selectOne("board.getTotalContentCountById",board_id);
	}
}
