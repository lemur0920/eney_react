package eney.api.v2.web;


import java.net.UnknownHostException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import eney.domain.MessageVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eney.api.v1.domain.ApiCommonVo;
import eney.api.v1.service.ApiMainService;
import eney.api.v2.domain.ApiMessageVO;
import eney.domain.AgentVO;
import eney.domain.MessageVO;
import eney.service.MessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(value="/apis/v2/*")
@Api(value="v2", produces="application/json", protocols="http/https(권장)", tags={"v2 메시지 전송"})
public class ApiMessageController {
	
	  
	@Resource
	MessageService messageService;

	@Resource
	ApiMainService apiMainService;
	
	@ApiOperation(value = "메시지 전송", notes = "API를 이용하여 메시지를 전송합니다.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 조회했습니다."),
			@ApiResponse(code = 400, message = "입력 데이터 값의 형식이 유효하지 않습니다"),
			@ApiResponse(code = 401, message = "토큰 인증 실패"), @ApiResponse(code = 405, message = "해당 요청을 지원하지 않습니다"),
			@ApiResponse(code = 406, message = "해당 미디어 타입을 지원하지 않습니다 (application/json만 지원)"),
			@ApiResponse(code = 500, message = "해당 요청을 처리중 서버에서 오류가 발생하였습니다") })
	@RequestMapping(value = "/message/send", method = RequestMethod.POST)
	public @ResponseBody ApiCommonVo<Boolean> sendMessage(HttpServletResponse response,
			@ApiParam(value = "회원 아이디", required = true) @RequestParam String userid,
			@ApiParam(value = "토큰 키", required = true) @RequestParam String tokenKey,
			@ApiParam(value = "메시지 타입", required = true, allowableValues="1,3,6,7") @RequestParam String msg_type,
			@ApiParam(value = "발신번호", required = true, example = "01012345678") @RequestParam String dstaddr,
			@ApiParam(value = "수신번호", required = true, example = "01012345678") @RequestParam String callback,
			@ApiParam(value = "제목(LMS/SMS 사용시 제목이 필수이므로 기본값 설정이 필요합니다.)", required = false) @RequestParam String subject,
			@ApiParam(value = "메시지 내용", required = true, example = "안녕하세요. 메시지입니다.") @RequestParam String text,

			@ApiParam(value = "파일 위치1",  required = false) @RequestParam(required = false) String fileloc1,
			@ApiParam(value = "파일 위치2",  required = false) @RequestParam(required = false) String fileloc2,
			@ApiParam(value = "파일 위치3",  required = false) @RequestParam (required = false)String fileloc3,
			@ApiParam(value = "파일 위치4",  required = false) @RequestParam(required = false) String fileloc4,
			@ApiParam(value = "파일 위치5",  required = false) @RequestParam(required = false) String fileloc5

	){

		MessageVO messageVO = new MessageVO();

		messageVO.setUserid(userid);
		messageVO.setMsg_type(msg_type);
		messageVO.setDstaddr(dstaddr);
		messageVO.setCallback(callback);
		messageVO.setSubject(subject);
		messageVO.setText(text);
		messageVO.setText2(text);
		Integer count = 0;
		if(fileloc1 != null){count ++;}
		if(fileloc2 != null){count ++;}
		if(fileloc3 != null){count ++;}
		if(fileloc4 != null){count ++;}
		if(fileloc5 != null){count ++;}

		messageVO.setFilecnt(String.valueOf(count));
		messageVO.setFileloc1(fileloc1);
		messageVO.setFileloc1(fileloc2);
		messageVO.setFileloc1(fileloc3);
		messageVO.setFileloc1(fileloc4);
		messageVO.setFileloc1(fileloc5);

		return apiMainService.getSendMessageStatus(tokenKey,messageVO);
		
	}
		 
}
