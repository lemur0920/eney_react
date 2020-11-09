package eney.web;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import eney.domain.BoardConfigVO;
import eney.domain.BoardContentVO;
import eney.domain.FileVO;
import eney.domain.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import eney.service.BoardService;
import eney.service.FileService;

@RequestMapping("/board")
@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
	
	@Resource
	private FileService fileService;
	@Resource
	private BoardService boardService;
	
	/**
	 * 게시판 삭제
	 * @param boardContentVO
	 * @param principal
	 * @throws AccessDeniedException
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	@RequestMapping(value="/delete/{content_id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer content_id){

		BoardContentVO boardContentVO = new BoardContentVO();
		boardContentVO.setContent_id(content_id);

		try {
			boardService.delete(boardContentVO);
			return new ResponseEntity<>(true,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 게시판 게시글 리스트 출력(GET)
	 * @param boardContentVO
	 * @param board_name
	 * @return
	 * @throws Throwable
	 **/
//	@RequestMapping(value="/{board_name}", method=RequestMethod.GET)
	@RequestMapping(method=RequestMethod.GET)
	public  ResponseEntity<?> boardList(@RequestParam(value="type", defaultValue="notice") String type, @RequestParam(value="page", defaultValue="1") Integer page) throws Throwable{

		System.out.println("게시판 리스트");

		Map<String, Object> map = new HashMap<>();

		try {
			BoardConfigVO boardConfigVO = boardService.getBoardConfigByPath(type);
			BoardContentVO boardContentVO = new BoardContentVO();
			boardContentVO.setPageNo(page);
			map = boardService.getBoardContentListById(boardContentVO, boardConfigVO.getBoard_id());
			map.put("boardInfo",boardConfigVO);

		} catch (Exception ex) {
			String errorMessage;
			errorMessage = ex + " <== error";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}



		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	/**
	 * 게시글 상세 보기
	 * @param content_id
	 * @param boardContentVO
	 * @return
	 * @throws Throwable
	 */

	@RequestMapping(value="/view" , method=RequestMethod.GET)
	public  ResponseEntity<?> boardView(@RequestParam(value="type") String type, @RequestParam(value="id") Integer id) throws Throwable{
//	public ModelAndView boardDetailView(@RequestParam Integer content_id, @ModelAttribute BoardContentVO boardContentVO, @PathVariable String board_name) throws Throwable{
//		ModelAndView mav = new ModelAndView("board/detail");
		boardService.updateHits(id);

		BoardConfigVO boardConfigVO = boardService.getBoardConfigByPath(type);
		BoardContentVO boardContentVO = new BoardContentVO();
		boardContentVO.setContent_id(id);
		boardContentVO.setBoard_id(boardConfigVO.getBoard_id());
		BoardContentVO board = boardService.get(boardContentVO);

		FileVO file = fileService.getFileVoInfo(type, id);

		Map<String, Object> map = new HashMap<>();

		map.put("board",board);
		map.put("boardInfo",boardConfigVO);
		map.put("files",file);

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@RequestMapping(value="/boardInfo" , method=RequestMethod.GET)
	public  ResponseEntity<?> boardInfo(@RequestParam(value="type") String type) throws Throwable{

		BoardConfigVO boardConfigVO = boardService.getBoardConfigByPath(type);

		return new ResponseEntity<>(boardConfigVO, HttpStatus.OK);
	}
	
	/**
	 * 게시판 게시글 리스트 출력(POST)
	 * @param boardContentVO
	 * @param board_name
	 * @throws Throwable
	 */
//	@RequestMapping(value="/{board_name}/index.do", method=RequestMethod.POST)
//	public ModelAndView boardIndexLogic(@ModelAttribute BoardContentVO boardContentVO, @PathVariable String board_name)
//			throws Throwable{
//		return boardView(boardContentVO,board_name,null);
//
//	}
	
	/**
	 * 관리자 계정에서 게시판 글쓰기 페이지 가져오기(GET)
	 * @param boardContentVO
	 * @param board_name
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/{board_name}/write.do", method=RequestMethod.GET)
	public ModelAndView boardWriteView(@ModelAttribute BoardContentVO boardContentVO, @PathVariable String board_name){
		ModelAndView mav = new ModelAndView("board/form");
		BoardConfigVO boardConfigVO = boardService.getBoardConfigByPath(board_name);
		boardContentVO.setStep("WRITE");
		boardContentVO.setBoard_name(board_name);
		mav.addObject("boardContentVO", boardContentVO);
		mav.addObject("boardConfigVO", boardConfigVO);
		return mav;
	}
	
	/**
	 * 관리자 계정에서 게시판 글쓰기 (POST)
	 * @param boardContentVO
	 * @param board_name
	 * @param principal
	 * @param request
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/{board_name}/write", method=RequestMethod.POST)
	public ResponseEntity<?> boardWrite(@ModelAttribute BoardContentVO boardContentVO, @PathVariable String board_name,
										Principal principal, HttpServletRequest request, Authentication authentication) throws Exception{

		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

		String userid = user.getUserId();

		BoardConfigVO boardConfigVO = boardService.getBoardConfigByPath(board_name);

		System.out.println("---AAAAAAAAA");
		System.out.println(boardContentVO);
		System.out.println(boardContentVO.getFiles());
		System.out.println("---AAAAAAAAA");


//		ModelAndView mav = new ModelAndView("board/form");
//		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boardContentVO.setWriter_id(userid);
		boardContentVO.setBoardConfigVO(boardConfigVO);

		//폼 데이터 로직
		boardContentVO.setBoard_id(boardConfigVO.getBoard_id());
		boardService.insertBoardContent(boardContentVO);

		//파일 업로드 로직
		List<MultipartFile> files = boardContentVO.getFiles();
		List<FileVO> fileVOList = fileService.processUpload(files, board_name, boardContentVO.getContent_id(), request);

		logger.debug("CustomLog:::" + fileVOList);
//		mav.setViewName("redirect:index.do");
//		return mav;
		return new ResponseEntity<>("성공",HttpStatus.OK);
	}
	
	/**
	 * 게시판 글 수정 페이지(GET)
	 * @param content_id
	 * @param boardContentVO
	 * @param board_name
	 */
//	@RequestMapping(value="/{board_name}/update.do", method=RequestMethod.GET)
//	public ModelAndView boardUpdateView(@RequestParam Integer content_id, @RequestBody BoardContentVO boardContentVO, @PathVariable String board_name){
//		ModelAndView mav = new ModelAndView("board/form");
//		boardContentVO.setContent_id(content_id);
//		boardContentVO = boardService.get(boardContentVO);
//		boardContentVO.setStep("UPDATE");
//		boardContentVO.setBoard_name(board_name);
//		mav.addObject("boardContentVO",boardContentVO);
//		return mav;
//	}
	
	/**
	 * 게시판 글 수정(POST)
	 * @param boardContentVO
	 * @param board_name
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/{board_name}/update", method=RequestMethod.POST)
	public ResponseEntity<?> boardUpdate(@ModelAttribute BoardContentVO boardContentVO, @PathVariable String board_name,
										HttpServletRequest request) throws Exception{


		System.out.println("3333");
		System.out.println(boardContentVO);
		System.out.println("3333");
		//폼 데이터 로직
		boardService.updateBoardContent(boardContentVO);
		//TODO 일단 업데이트시 기존 파일 업로드한거 매칭 삭제 시켜주기만함 차후 수정해야함!!
		fileService.updateFile(boardContentVO);

		//파일 업로드 로직
		List<MultipartFile> files = boardContentVO.getFiles();
		List<FileVO> fileVOList = fileService.processUpload(files, board_name, boardContentVO.getContent_id(), request);
		logger.debug("CustomLog:::" + fileVOList);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
