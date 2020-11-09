package eney.api.v1.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ApiReturnModel", description="API 요청 처리 후 결과를 나타냅니다.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@XmlRootElement
public class ApiCommonVo<T> {
	
	@JsonProperty("statusCode")
	@ApiModelProperty(value = "HTTP Status code", example="200", required=true)
	private int status_code;
	
	@JsonProperty("resource")
	@ApiModelProperty(value = "Resource")
	private String resource;
	
	@JsonProperty("description")
	@ApiModelProperty(value = "오류 정보")
	private String description;
	
	@JsonProperty("info")
	@ApiModelProperty(value = "(요청 처리시)요청 처리 결과")
	private T info;
	
	public ApiCommonVo(){}
	
	public ApiCommonVo(int status_code, String resource, String description, T info) {
		this.status_code = status_code;
		this.resource = resource;
		this.description = description;
		this.info = info;
	}
	
	public int getStatus_code() {
		return status_code;
	}
	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public T getInfo() {
		return info;
	}
	public void setInfo(T info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "ApiCommonVo [status_code=" + status_code + ", resource=" + resource + ", description=" + description
				+ ", info=" + info + "]";
	}	
}
