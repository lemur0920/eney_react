package eney.api.v2.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="message")
public class ApiMessageVO<T> {
	
	@JsonProperty("message")
	@ApiModelProperty(value="메시지 내용", example="안녕하세요. 메시지입니다.")
	private int message;
	
	
	@JsonProperty("vno")
	@ApiModelProperty(value="발신번호", example="01012345678")
	private int vno;
	
	@JsonProperty("phone_number")
	@ApiModelProperty(value="수신번호", example="01099999999")
	private int phone_number;
		
}


