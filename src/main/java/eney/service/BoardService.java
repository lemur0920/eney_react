package eney.service;

import java.nio.file.AccessDeniedException;
import java.util.*;

import javax.annotation.Resource;

import eney.domain.BoardConfigVO;
import eney.domain.BoardContentVO;
import eney.domain.UserVO;
import eney.mapper.BoardDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

	@Resource
    BoardDao boardDao;

	/**
	 * 게시글 상세 정보 출력 
	 * @param boardContentVO content_id 기준
	 * @return
	 */
	public BoardContentVO get(BoardContentVO boardContentVO){
		BoardContentVO board = boardDao.get(boardContentVO);
		return board;
	}

	/**
	 * 조회수 업데이트
	 * @param int
	 * @return
	 */
	public int updateHits(int contentId){
		return boardDao.updateHits(contentId);
	}

	/**
	 * 게시판 게시글 삭제(ADMIN만 가능)
	 * @param boardContentVO
	 * @throws AccessDeniedException
	 */
	public void delete(BoardContentVO boardContentVO) {
		boardDao.delete(boardContentVO);
//		BoardContentVO content_db = get(boardContentVO);
//		boolean check = false;
//		if(content_db.getBoard_id() == 1){
//			@SuppressWarnings("unchecked")
//			Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//
//			for (SimpleGrantedAuthority authoritity : authorities) {
//				if(String.valueOf(authoritity).equals("ROLE_ADMIN")){
//					check = true;
//				}
//			}
//		}else{
//			if(content_db.getWriter_id().equals(userVO.getUserid()))
//				check = true;
//		}
//		if(check){
//			/*	boardDao.insertBoardContentTobeDeleted(content_db);*/
//			boardDao.delete(boardContentVO);
//		}
//		else
//			throw new AccessDeniedException(userVO.getUserid());
	}

	public void update(BoardContentVO boardContentVO, UserVO userVO){

	}

	/**
	 * 게시판 게시글 등록
	 * @param boardContentVO
	 * @throws AccessDeniedException
	 */

	public void insertBoardContent(BoardContentVO boardContentVO) throws AccessDeniedException {
		String board_auth = boardContentVO.getBoardConfigVO().getWriter_auth();
		StringTokenizer st = new StringTokenizer(board_auth,",");
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		boolean authCheck  = false;
		while(st.hasMoreTokens()){
			String element = st.nextToken();
			for (SimpleGrantedAuthority authority : authorities) {
				if(String.valueOf(authority).equals(element)){
					authCheck = true;
					break;
				}
			}
		}
		if(authCheck){
			boardDao.insertBoardContent(boardContentVO);
		}
		else
			throw new AccessDeniedException(boardContentVO.getWriter_id());

	}
	/**
	 * 게시글 수정
	 * @param boardContentVO
	 */
	public void updateBoardContent(BoardContentVO boardContentVO) {
		boardDao.updateBoardContent(boardContentVO);
	}

	/**
	 * 게시판 카테고리 검색
	 * @param board_name 게시판 이름
	 * @return 게시판 카테고리
	 */
	public BoardConfigVO getBoardConfigByPath(String board_name) {
		return boardDao.getBoardConfigByPath(board_name);
	}

	public Map<String, Object> getBoardContentListById(BoardContentVO boardContentVO, int board_id) {

		boardContentVO.setTotalCount(boardDao.getTotalContentCountById(board_id));
		boardContentVO.setBoard_id(board_id);

		Map<String, Object> map = new HashMap<>();

		List<BoardContentVO> boardList = boardDao.getBoardContentListById(boardContentVO);

		map.put("boardList", boardList);
		map.put("page", boardContentVO);

		return map;
	}
}
