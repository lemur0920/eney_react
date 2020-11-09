package eney.domain;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
public class BoardContentVO extends PageVO{
	
	/*FINAL*/
	public final static int BOARD_ID_NOTICE = 1;
	public final static int BOARD_ID_RECRUIT = 2;
	
	private int content_id;
//	private Integer rownum;
	private int board_id;
	private String title;
	private String content;
	private String writer_id;
	private int hits;
	private String write_date;
	private String update_date;
	
	private Date start_date;
	private Date end_date;
	
	private String step;

	private List<MultipartFile> files;
	private List<FileVO> fileVO_list;
	
	private String board_name;
	private BoardConfigVO boardConfigVO;
	
	private boolean pagination_flag;

	@Setter
	@Getter
	private int group_number;

	@Setter
	@Getter
	private int condition_number;

	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

//	public Integer getRownum() {
//		return rownum;
//	}
//	public void setRownum(Integer rownum) {
//		this.rownum = rownum;
//	}

	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public List<FileVO> getFileVO_list() {
		return fileVO_list;
	}
	public void setFileVO_list(List<FileVO> fileVO_list) {
		this.fileVO_list = fileVO_list;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public BoardConfigVO getBoardConfigVO() {
		return boardConfigVO;
	}
	public void setBoardConfigVO(BoardConfigVO boardConfigVO) {
		this.boardConfigVO = boardConfigVO;
	}

	public boolean isPagination_flag() {
		return pagination_flag;
	}
	public void setPagination_flag(boolean pagination_flag) {
		this.pagination_flag = pagination_flag;
	}
	
	
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public int getGroup_number() {
		return group_number;
	}
	public void setGroup_number(int group_number) {
		this.group_number = group_number;
	}

	public int getCondition_number() {
		return condition_number;
	}
	public void setCondition_number(int condition_number) {
		this.condition_number = condition_number;
	}


}
