package eney.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties({"total_item_num", "total_page_num", "page_per_item_num","review_page_per_item_num", "present_page", "present_first_item_idx", "present_last_item_idx", "page_bar_idx_num", "page_bar_first_num", "page_bar_last_num"})
public class Pagination{
	
	//총 item 수
	@JsonProperty("totalItemNum")
	@ApiModelProperty(value="전체 검색 결과의 수", hidden=true)
	private int total_item_num;

	//총 페이지 수
	@JsonProperty("totalPageNum")
	@ApiModelProperty(value="보여줄 수 있는 페이지 수", hidden=true)
	private int total_page_num;

	@ApiModelProperty(value="보여줄 수 있는 페이지 수", hidden=true)
	private int review_total_page_num;

	//페이지당 item 수
	@JsonProperty("pagePerItemNum")
	@ApiModelProperty(value="한 페이지에 출력될 리스트 수", hidden=true)
	private int page_per_item_num = 10;

	//리뷰 페이지당 items
	@ApiModelProperty(value="한 페이지에 출력될 리스트 수", hidden=true)
	private int review_page_per_item_num = 3;
	//현재 페이지
	@JsonProperty("presentPage")
	@ApiModelProperty(value="현제 페이지", hidden=true)
	private int present_page = 1;
	
	
	//현재 페이지 처음
	@JsonProperty("presentFirstItemIdx")
	@ApiModelProperty(hidden=true)
	private int present_first_item_idx;
	
	//현재 페이지 마지막
	@JsonProperty("presentLastItemIdx")
	@ApiModelProperty(hidden=true)
	private int present_last_item_idx;
	
	//페이지 바 수 개수
	@JsonProperty("pageBarIdxNum")
	@ApiModelProperty(hidden=true)
	private int page_bar_idx_num = 5;
	
	//페이지 바 처음 수
	@JsonProperty("pageBarFirstNum")
	@ApiModelProperty(hidden=true)
	private int page_bar_first_num;
	
	//페이지 바 마지막 수
	@JsonProperty("pageBarLastNum")
	@ApiModelProperty(hidden=true)
	private int page_bar_last_num;


	// 페이지 그룹 관련 개수 4라는 뜻은 1 2 3 4 > 까지만 뜨고
	// > 누를 시에 < 5 6 7 8 > 이런식으로 페이지 구성 된다는 뜻
	private int pageNumberPerPageGroup = 5;



	public Pagination(){
		
	}

	public Pagination(int total_item_num) {
		this.total_item_num = total_item_num;
	}

	public Pagination(int total_item_num, int present_page) {
		this.total_item_num = total_item_num;
		this.present_page = present_page;
	}

	public Pagination(Pagination target){
		this.total_item_num = target.total_item_num;
		this.total_page_num = target.total_item_num;
		this.page_per_item_num = target.page_per_item_num;
		this.present_page = target.present_page;
		this.present_first_item_idx = target.present_first_item_idx;
		this.present_last_item_idx = target.present_last_item_idx;
		this.page_bar_idx_num = target.page_bar_idx_num;
		this.page_bar_first_num = target.page_bar_first_num;
		this.page_bar_last_num = target.page_bar_last_num;
	}
	
	
	
	public int getReview_total_page_num() {
		int tmp = total_item_num / review_page_per_item_num;
		
		//if(tmp%page_per_item_num>0)
		if(total_item_num%review_page_per_item_num>=1)
			tmp+=1;
		
		//if(tmp == 0)
		//	tmp = 1;
		
		return tmp;
	}

	public void setReview_total_page_num(int review_total_page_num) {
		this.review_total_page_num = review_total_page_num;
	}

	public int getTotal_item_num() {
		return total_item_num;
	}
	
	public void setTotal_item_num(int total_item_num) {
		this.total_item_num = total_item_num;
	}
	
	public int getTotal_page_num() {
		int tmp = total_item_num / page_per_item_num;
		
		//if(tmp%page_per_item_num>0)
		if(total_item_num%page_per_item_num>=1)
			tmp+=1;
		
		//if(tmp == 0)
		//	tmp = 1;
		
		return tmp;
	}
	
	
	
	
	public void setTotal_page_num(int total_page_num) {
		this.total_page_num = total_page_num;
	}
	
	public int getPage_per_item_num() {
		return page_per_item_num;
	}     
	
	public void setPage_per_item_num(int page_per_item_num) {
		this.page_per_item_num = page_per_item_num;
	}
	
	public int getPresent_page() {
		return present_page;
	}
	
	public void setPresent_page(int present_page) {
		this.present_page = present_page;
	}
	
	public int getPresent_first_item_idx() {
//		int res;
//		//res = total_item_num - page_per_item_num * (present_page - 1) - (page_per_item_num - 1);
//		res  = 1 + (present_page - 1) * page_per_item_num;
//
//		if(res <= 0)
//			res = 1;
//
//		return res;

		return 1 + (present_page - 1) * page_per_item_num <= 0 ? 1 : 1 + (present_page - 1) * page_per_item_num;
		
	}
	
	public void setPresent_first_item_idx(int present_first_item_idx) {
		this.present_first_item_idx = present_first_item_idx;
	}
	
	public int getPresent_last_item_idx() {
		
		int res;
		//res = total_item_num - page_per_item_num * (present_page - 1);
		res  = present_page * page_per_item_num;
		
		if(res <= 0)
			res = page_per_item_num;
		
		return res;
		
	}
	
	public void setPresent_last_item_idx(int present_last_item_idx) {
		this.present_last_item_idx = present_last_item_idx;
	}

	public int getPage_bar_first_num() {
		int tmp = page_bar_idx_num / 2;
		int res = present_page - tmp;
		
		if(present_page + tmp > getTotal_page_num()){
			int a = present_page + tmp - getTotal_page_num();
			res -= a;
		}
			
		if(res < 1)
			res = 1;
		
		return res;
	}

	public void setPage_bar_first_num(int page_bar_first_num) {
		this.page_bar_first_num = page_bar_first_num;
	}
	
	public int getPage_bar_last_num() {
		int tmp = page_bar_idx_num / 2;
		int res = present_page + tmp;
		
		if(getTotal_page_num()<=page_bar_idx_num){
			res = getTotal_page_num();
		}
		else{
			if(res < page_bar_idx_num)
				res = page_bar_idx_num;
				
			if(res >= getTotal_page_num())
				res = getTotal_page_num();
		}
		
		return res;
	}

	public void setPage_bar_last_num(int page_bar_last_num) {
		this.page_bar_last_num = page_bar_last_num;
	}

	public int getPage_bar_idx_num() {
		return page_bar_idx_num;
	}

	public void setPage_bar_idx_num(int page_bar_idx_num) {
		this.page_bar_idx_num = page_bar_idx_num;
	}

	
	
	public int getReview_page_per_item_num() {
		return review_page_per_item_num;
	}

	public void setReview_page_per_item_num(int review_page_per_item_num) {
		this.review_page_per_item_num = review_page_per_item_num;
	}


    // 여기서 부터 정재영 제작 마치는 부분까지 표시 있음 ! 페이징 처리 관련해서

	public int getPageNumberPerPageGroup() {
		return pageNumberPerPageGroup;
	}

	public void setPageNumberPerPageGroup(int pageNumberPerPageGroup) {
		this.pageNumberPerPageGroup = pageNumberPerPageGroup;
	}




	/**
	 * 총 페이지 수를 return한다.<br>
	 * 1. 전체 데이터(게시물) % 한 페이지에 보여줄 데이터 개수 <br>
	 * => 0 이면 둘을 / 값이 총 페이지 수<br>
	 * 2. 전체 데이터(게시물) % 한 페이지에 보여줄 데이터 개수 <br>
	 * => 0보다 크면 둘을 / 값에 +1을 한 값이 총 페이지 수<br>
	 * 게시물수 1 2 3 4 5 6 7 8 9 10 11 12<br>
	 * 1페이지 1~5<br>
	 * 2페이지 6~10<br>
	 * 3페이지 11 <br>
	 * ex) 게시물 32 개 , 페이지당 게시물수 5개-> 7 페이지
	 *
	 * @return
	 */

	public int getTotalCampaignPage(){
		int num = this.total_item_num % this.page_per_item_num;
		int totalPage;

		if (num == 0) {
			totalPage = this.total_item_num / this.page_per_item_num;
		} else {
			totalPage = this.total_item_num / this.page_per_item_num + 1;
		}

		return totalPage;
	}

	/**
	 * 현재 페이지에서 보여줄 게시물 행(row)의 마지막 번호 현재페이지*contentNumberPerPage 만약 총게시물수보다<br>
	 * 연산결과의 번호가 클 경우 총게시물수가 마지막 번호가 되어야 한다 ex) 총게시물수 7 개 총페이지는 2페이지 : 1 2 3 4 5
	 * <br>
	 * | 6 7 | 1page 2page 현재페이지는 2페이지이고 2*5(페이지당 게시물수) 는 10 이고 실제 마지막 번호 7이다 ->
	 * <br>
	 * 연산결과가 총게시물수보다 클 경우 총게시물수가 마지막번호가 되어야 함
	 *
	 * @return
	 */
	public int getEndRowNumber() {
		int endRowNumber = total_item_num * page_per_item_num;
		if (total_item_num < endRowNumber)
			endRowNumber = total_item_num;
		return endRowNumber;
	}


	/**
	 * 총 페이지 그룹의 수를 return한다.<br>
	 * 1. 총 페이지수 % Page Group 내 Page 수. <br>
	 * => 0 이면 둘을 / 값이 총 페이지 수<br>
	 * 2. 총 페이지수 % Page Group 내 Page 수. <br>
	 * => 0보다 크면 둘을 / 값에 +1을 한 값이 총 페이지 수<br>
	 * ex) 총 게시물 수 23 개 <br>
	 * 총 페이지 ? 총 페이지 그룹수 ? <br>
	 * 페이지 1 2 3 4 5<br>
	 * 페이지그룹 1234(1그룹) 5(2그룹)<br>
	 *
	 */
	public int getTotalPageGroup() {
		int num = this.getTotalCampaignPage() % this.pageNumberPerPageGroup;
		int totalPageGroup = 0;
		if (num == 0) {
			totalPageGroup = this.getTotalCampaignPage() / this.pageNumberPerPageGroup;
		} else {
			totalPageGroup = this.getTotalCampaignPage() / this.pageNumberPerPageGroup + 1;
		}
		return totalPageGroup;
	}

	/**
	 * 현재 페이지가 속한 페이지 그룹 번호(몇 번째 페이지 그룹인지) 을 return 하는 메소드 <br>
	 * 1. 현재 페이지 % Page Group 내 Page 수 => 0 이면 <br>
	 * 둘을 / 값이 현재 페이지 그룹. <br>
	 * 2. 현재 페이지 % Page Group 내 Page 수 => 0 크면 <br>
	 * 둘을 / 값에 +1을 한 값이 현재 페이지 그룹<br>
	 * 페이지 1 2 3 4 /5 6 7 8/ 9 10 1그룹 2그룹 3그룹
	 *
	 * @return
	 */

	public int getNowPageGroup() {
		int num = this.present_page % this.pageNumberPerPageGroup;
		int nowPageGroup = 0;
		if (num == 0) {
			nowPageGroup = this.present_page / this.pageNumberPerPageGroup;
		} else {
			nowPageGroup = this.present_page / this.pageNumberPerPageGroup + 1;
		}
		return nowPageGroup;
	}


	/**
	 * 현재 페이지가 속한 페이지 그룹의 시작 페이지 번호를 return 한다.<br>
	 * Page Group 내 Page 수*(현재 페이지 그룹 -1) + 1을 한 값이 첫 페이지이다.<br>
	 * (페이지 그룹*페이지 그룹 개수, 그룹의 마지막 번호이므로) <br>
	 * 페이지 그룹 <br>
	 * 1 2 3 4 -> 5 6 7 8 -> 9 10 <br>
	 *
	 * @return
	 */
	public int getStartPageOfPageGroup() {
		int num = this.pageNumberPerPageGroup * (this.getNowPageGroup() - 1) + 1;
		return num;
	}

	/**
	 * 현재 페이지가 속한 페이지 그룹의 마지막 페이지 번호를 return 한다.<br>
	 * 1. 현재 페이지 그룹 * 페이지 그룹 개수 가 마지막 번호이다. <br>
	 * 2. 그 그룹의 마지막 페이지 번호가 전체 페이지의 마지막 페이지 번호보다 <br>
	 * 큰 경우는 전체 페이지의 마지막 번호를 return 한다.<br>
	 * 1 2 3 4 -> 5 6 7 8 -> 9 10
	 *
	 * @return
	 */
	public int getEndPageOfPageGroup() {
		int num = this.getNowPageGroup() * this.pageNumberPerPageGroup;
		if (this.getTotalCampaignPage() < num) {
			num = this.getTotalCampaignPage();
		}
		return num;
	}

	/**
	 * 이전 페이지 그룹이 있는지 체크하는 메서드 <br>
	 * 현재 페이지가 속한 페이지 그룹이 1보다 크면 true<br>
	 * ex ) 페이지 1 2 3 4 / 5 6 7 8 / 9 10 <br>
	 * 1 2 3 group
	 *
	 * @return
	 */
	public boolean isPreviousPageGroup() {
		boolean flag = false;
		if (this.getNowPageGroup() > 1) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 다음 페이지 그룹이 있는지 체크하는 메서드 <br>
	 * 현재 페이지 그룹이 마지막 페이지 그룹(<br>
	 * 마지막 페이지 그룹 == 총 페이지 그룹 수) 보다 작으면 true<br>
	 * * ex ) 페이지 <br>
	 * 1 2 3 4 / 5 6 7 8 / 9 10 <br>
	 * 1 2 3 group
	 *
	 * @return
	 */
	public boolean isNextPageGroup() {
		boolean flag = false;
		if (this.getNowPageGroup() < this.getTotalPageGroup()) {
			flag = true;
		}
		return flag;
	}

	// 페이징 처리 관련 정재영 .





	@Override
	public String toString() {
		return "Pagination [total_item_num=" + total_item_num
				+ ", total_page_num=" + total_page_num + ", page_per_item_num="
				+ page_per_item_num + ", present_page=" + present_page
				+ ", present_first_item_idx=" + present_first_item_idx
				+ ", present_last_item_idx=" + present_last_item_idx
				+ ", page_bar_idx_num=" + page_bar_idx_num
				+ ", page_bar_first_num=" + page_bar_first_num
				+ ", page_bar_last_num=" + page_bar_last_num + "]";
	}
}
