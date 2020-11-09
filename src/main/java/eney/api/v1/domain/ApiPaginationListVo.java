package eney.api.v1.domain;

import java.util.List;

import eney.domain.PageVO;
import eney.domain.Pagination;
import com.fasterxml.jackson.annotation.JsonProperty;

import eney.domain.Pagination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="paginationList", description="페이지 정보와 조회 결과 리스트를 담고 있습니다.")
public class ApiPaginationListVo<T> {
	@JsonProperty("list")
	@ApiModelProperty(value="조회 결과 리스트")
	List<T> list;
	
	//총 item 수 
	@JsonProperty("itemCount")
	@ApiModelProperty(value="전체 검색 결과의 수", example="35")
	private int item_count;
	
	//총 페이지 수 
	@JsonProperty("pageCount")
	@ApiModelProperty(value="보여줄 수 있는 페이지 수", example="4")
	private int page_count;
	
	//페이지당 item 수 
	@JsonProperty("result")
	@ApiModelProperty(value="한 페이지에 출력될 리스트 수", example="10")
	private int result = 10;
	
	//현재 페이지
	@JsonProperty("presentPage")
	@ApiModelProperty(value="현제 페이지", example="1")
	private int present_page = 1;
	
//	public ApiPaginationListVo(List<T> list, Pagination paginationInfo) {
	public ApiPaginationListVo(List<T> list, PageVO pageVO) {
		this.list = list;
		
		this.item_count = pageVO.getTotalCount();
		this.page_count = pageVO.getFinalPageNo();
		this.result = pageVO.getPageSize();
		this.present_page = pageVO.getPageNo();
	}

	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	public int getItem_count() {
		return item_count;
	}
	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}

	public int getPage_count() {
		return page_count;
	}
	public void setPage_count(int page_count) {
		this.page_count = page_count;
	}

	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}

	public int getPresent_page() {
		return present_page;
	}
	public void setPresent_page(int present_page) {
		this.present_page = present_page;
	}

	@Override
	public String toString() {
		return "ApiPaginationListVo [list=" + list + ", item_count=" + item_count + ", page_count=" + page_count
				+ ", result=" + result + ", present_page=" + present_page + "]";
	}
}
