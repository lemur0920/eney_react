package eney.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import eney.domain.PaymentLogVo.PayStatus;

@ToString
@Getter
@Setter
public class RecordVO extends Pagination{
	private Integer idx;
	private String userid;
	private String service_type;
	private String service_period;
	private int service_amount;
	private String pay_way;
	private String corporate_address;
	private String ceo_name;
	private String end_date;
	private PaymentLogVo.PayStatus pay_state;
	private String generate_yn;
	private String publish_email;
	private String reg_date;
	private String note;
	private List<MultipartFile> files;
	private List<FileVO> fileVO_list;
	
	private List<Integer> close_service;
	
	private boolean pagination_flag;


}	