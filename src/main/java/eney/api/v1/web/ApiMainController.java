package eney.api.v1.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Size;

import eney.service.AcsService;
import eney.service.SupplyService;
import eney.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import eney.api.v1.domain.ApiCommonVo;
import eney.api.v1.domain.ApiPaginationListVo;
import eney.api.v1.domain.ApiTokenVo;
import eney.api.v1.service.ApiMainService;
import eney.domain.AgentVO;
import eney.domain.CallLogVO;
import eney.domain.UserVO;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import eney.api.v1.domain.ApiCommonVo;
import eney.api.v1.domain.ApiPaginationListVo;
import eney.api.v1.domain.ApiTokenVo;
import eney.api.v1.service.ApiMainService;
import eney.mapper.AdminDao;
import eney.mapper.MainDao;
import eney.mapper.SupplyDao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value="/apis/v1/*")
@Api(value="v1", produces="application/json", protocols="http/https(권장)", tags={"v1 050번호 관리 API"})
public class ApiMainController {
	
  @Resource
  ApiMainService apiMainService;

  @Resource
  SupplyService supplyService;

  @Resource
  AcsService acsService;

  @Resource
  MainDao mainDao;
  @Resource
  AdminDao adminDao;
  @Resource
  SupplyDao supplyDao;
  
  /**
   * 050 번호 설정 수정
   * @param response
   * @param vno 050번호
   * @param tokenKey 토큰키
   * @param sms CallBack SMS 내용
   * @return API 처리 결과
   */
  @ApiOperation(value="050 번호 설정 수정", notes="사용자가 보유하고 있는 050번호의 설정을 변경합니다. (필수 입력이 아닌 부분은 값을 입력하지 않으면 수정되지 않습니다)", 
           produces=MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value={@ApiResponse(code=200, message="성공적으로 수정되었습니다."),
             @ApiResponse(code=400, message="입력 데이터 값의 형식이 유효하지 않습니다"),
             @ApiResponse(code=401, message="토큰 인증 실패"),
             @ApiResponse(code=403, message="해당 리소스를 사용할 권한이 없습니다."),
             @ApiResponse(code=405, message="해당 요청을 지원하지 않습니다"),
             @ApiResponse(code=406, message="해당 미디어 타입을 지원하지 않습니다 (application/json만 지원)"),
             @ApiResponse(code=500, message="해당 요청을 처리중 서버에서 오류가 발생하였습니다")})
  @RequestMapping(value="/050/{userid}/{vno}", method=RequestMethod.PATCH)
  public @ResponseBody
  ApiCommonVo<AgentVO> updateVno(HttpServletResponse response,
                                 @ApiParam(value="계정 ID", required=true) @PathVariable("userid") String userId,
                                 @ApiParam(value="050번호", example="05061234567", required=true)@PathVariable("vno") String vno,
                                 @ApiParam(value="토큰 키", required=true)@RequestParam String tokenKey,
                                 @ApiParam(value="착신번호", example="01012345678") @RequestParam(required=false) String rcvNo,
                                 @ApiParam(value="컬러링 번호", required=false) @RequestParam(required=false) Integer colorringIdx,
                                 @ApiParam(value="착신 멘트 번호", required=false) @RequestParam(required=false) Integer rcvmentIdx,
                                 @ApiParam(value="가맹점명", required=false) @RequestParam(required=false) String name,
                                 @ApiParam(value="발신지명", required=false ) @RequestParam(required=false) String dongName,
                                 @ApiParam(value="발신지명 안내 사용 여부", required=false, allowableValues="Y,N" )@RequestParam(required=false) String dongYn,
                                 @ApiParam(value="CallBack SMS 내용", required=false) @RequestParam(required=false) String sms,
                                 @ApiParam(value="CallBack SMS 사용 여부", required=false, allowableValues="Y,N") @RequestParam(required=false) String smsYn,
                                 @ApiParam(value="부재중 CallBack SMS 내용", required=false) @RequestParam(required=false) String out_sms,
                                 @ApiParam(value="부재중 CallBack SMS 사용 여부", required=false, allowableValues="Y,N") @RequestParam(required=false) String outSmsYn,
                                 @ApiParam(value="전체 메시지 발신 번호", required=false) @RequestParam(required=false) String callbackNo,
                                 @ApiParam(value="부재중 메시지 수신 번호", required=false) @RequestParam(required=false) String outSmsPhone
		  )
  {
	long startTime = System.currentTimeMillis();
	Map<String,String> map = new HashMap<>();
	map.put("type", "API 번호 수정");
	
    AgentVO updateVnoInfo = new AgentVO();
    updateVnoInfo.setVno(vno);
    updateVnoInfo.setRcv_no(rcvNo);
    updateVnoInfo.setColorring_idx(colorringIdx);
    updateVnoInfo.setRcvment_idx(rcvmentIdx);
    updateVnoInfo.setDong_name(dongName);
    updateVnoInfo.setDong_yn(dongYn);
    updateVnoInfo.setName(name);
    updateVnoInfo.setSms(sms);
    updateVnoInfo.setSms_yn(smsYn);
    updateVnoInfo.setOut_sms(out_sms);
    updateVnoInfo.setOut_sms_yn(outSmsYn);
    updateVnoInfo.setCallback_no(callbackNo);
    updateVnoInfo.setOut_sms_phone(outSmsPhone);

    ApiCommonVo<AgentVO> returnData = apiMainService.updateVno(userId, tokenKey, updateVnoInfo);
    response.setStatus(returnData.getStatus_code());
    
    long endTime = System.currentTimeMillis();
	
	map.put("time", ( endTime - startTime )/1000.0f +"초");
	mainDao.insertTimeMeasure(map);
    
	Map<String,Integer> enablePatchcall = adminDao.getEnablePatchCallSKB();
	supplyDao.skbUseNumber(enablePatchcall);
	
    return returnData;
  }
  
  /**
   * 050 수신내역 조회
   * @param response
   * @param vno 050번호
   * @param tokenKey 토큰키
   * @return API 처리 결과
   */
  @ApiOperation(value="수신내역 조회", notes="수신내역을 조회합니다. 필수 입력값을 제외한 나머지 필드(rcvNo, aniNo 등)에 데이터를 입력할 경우 해당!조건에 일치하는 로그를 조회합니다.",
        produces=MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value={@ApiResponse(code=200, message="성공적으로 조회했습니다."),
             @ApiResponse(code=400, message="입력 데이터 값의 형식이 유효하지 않습니다"),
             @ApiResponse(code=401, message="토큰 인증 실패"),
             @ApiResponse(code=405, message="해당 요청을 지원하지 않습니다"),
             @ApiResponse(code=406, message="해당 미디어 타입을 지원하지 않습니다 (application/json만 지원)"),
             @ApiResponse(code=500, message="해당 요청을 처리중 서버에서 오류가 발생하였습니다")})
  @RequestMapping(value="/calllog/{userid}/{vno}", method=RequestMethod.GET)
  public @ResponseBody ApiCommonVo<ApiPaginationListVo<CallLogVO>> getCallLog(HttpServletResponse response,
                                                                              @ApiParam(value="계정 ID", required=true) @PathVariable("userid") String userId,
                                                                              @ApiParam(value="050번호", example="05061234567", required=true)@PathVariable("vno") String vno,
                                                                              @ApiParam(value="토큰 키", required=true)@RequestParam String tokenKey,
                                                                              @ApiParam(value="착신번호", example="01012345678", required=false) @RequestParam(required=false) String rcvNo,
                                                                              @ApiParam(value="발신번호", example="01012345678", required=false) @RequestParam(required=false) String aniNo,
                                                                              @ApiParam(value="발신지명", required=false) @RequestParam(required=false) String dongName,
                                                                              @ApiParam(value="기간 시작일 (YYYYMMDD 형식)", example="20160131", required=false) @RequestParam(required=false) String periodFrom,
                                                                              @ApiParam(value="기간 종료일 (YYYYMMDD 형식)", example="20160131", required=false) @RequestParam(required=false) String periodTo,
                                                                              @ApiParam(value="기간 종료일 (YYYYMMDD 형식)", example="20160131", required=false) @RequestParam(required=false) String timeTo,
                                                                              @ApiParam(value="기간 종료일 (YYYYMMDD 형식)", example="20160131", required=false) @RequestParam(required=false) String timeFrom,
                                                                              @ApiParam(value="한 페이지에 출력될 리스트 수 (1 ~ 100)", required=false) @Size(min=1, max=100) @RequestParam(defaultValue="10", required=false) Integer result,
                                                                              @ApiParam(value="페이지 번호", required=false) @RequestParam(defaultValue="1", required=false) Integer pageNo){
    
	long startTime = System.currentTimeMillis();
	Map<String,String> map = new HashMap<>();
	map.put("type", "API 수신내역 조회");
	  
	CallLogVO searchQuery = new CallLogVO();
    searchQuery.setDong_name(dongName);
    searchQuery.setAni(aniNo);
    searchQuery.setCalled_no(rcvNo);
    searchQuery.setDn(vno);
    searchQuery.setSearch_period_from(periodFrom);
    searchQuery.setSearch_period_to(periodTo);
    searchQuery.setPageSize(result);
    searchQuery.setPageNo(pageNo);

    ApiCommonVo<ApiPaginationListVo<CallLogVO>> returnData = apiMainService.getCallLogList(userId, tokenKey, searchQuery);
    response.setStatus(returnData.getStatus_code());
    
    long endTime = System.currentTimeMillis();
	
	map.put("time", ( endTime - startTime )/1000.0f +"초");
	mainDao.insertTimeMeasure(map);
    
	Map<String,Integer> enablePatchcall = adminDao.getEnablePatchCallSKB();
	supplyDao.skbUseNumber(enablePatchcall);
	
    return returnData;
  }

  /**
   * 050 수신내역 조회
   * @param response
   * @param vno 050번호
   * @param tokenKey 토큰키
   * @return API 처리 결과
   */
  @ApiOperation(value="시간대별 수신내역 조회", notes="수신내역을 조회합니다. 필수 입력값을 제외한 나머지 필드(rcvNo, aniNo 등)에 데이터를 입력할 경우 해당!조건에 일치하는 로그를 조회합니다.",
          produces=MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value={@ApiResponse(code=200, message="성공적으로 조회했습니다."),
          @ApiResponse(code=400, message="입력 데이터 값의 형식이 유효하지 않습니다"),
          @ApiResponse(code=401, message="토큰 인증 실패"),
          @ApiResponse(code=405, message="해당 요청을 지원하지 않습니다"),
          @ApiResponse(code=406, message="해당 미디어 타입을 지원하지 않습니다 (application/json만 지원)"),
          @ApiResponse(code=500, message="해당 요청을 처리중 서버에서 오류가 발생하였습니다")})
  @RequestMapping(value="/calllog/{userid}", method=RequestMethod.GET)
  public @ResponseBody ApiCommonVo<ApiPaginationListVo<CallLogVO>> getCallLogSetTime(HttpServletResponse response,
     @ApiParam(value="계정 ID", required=true) @PathVariable("userid") String userId,
     @ApiParam(value="050번호", example="05061234567", required=false)@RequestParam(required=false) String vno,
     @ApiParam(value="토큰 키", required=true)@RequestParam String tokenKey,
     @ApiParam(value="착신번호", example="01012345678", required=false) @RequestParam(required=false) String rcvNo,
     @ApiParam(value="발신번호", example="01012345678", required=false) @RequestParam(required=false) String aniNo,
     @ApiParam(value="발신지명", required=false) @RequestParam(required=false) String dongName,
     @ApiParam(value="기간 시작일 (YYYYMMDD 형식)", example="20160131", required=false) @RequestParam(required=false) String periodFrom,
     @ApiParam(value="기간 종료일 (YYYYMMDD 형식)", example="20160131", required=false) @RequestParam(required=false) String periodTo,

     @ApiParam(value="시작시간 ((hhmmss형식)", example="235010", required=false) @RequestParam(required=false) String timeFrom,
     @ApiParam(value="종료시간 (hhmmss형식)", example="102030", required=false) @RequestParam(required=false) String timeTo,
     @ApiParam(value="한 페이지에 출력될 리스트 수 (1 ~ 100)", required=false) @Size(min=1, max=100) @RequestParam(defaultValue="10", required=false) Integer result,
     @ApiParam(value="페이지 번호", required=false) @RequestParam(defaultValue="1", required=false) Integer pageNo){

    long startTime = System.currentTimeMillis();
    Map<String,String> map = new HashMap<>();
    map.put("type", "API 수신내역 조회");

    CallLogVO searchQuery = new CallLogVO();
    searchQuery.setDong_name(dongName);
    searchQuery.setAni(aniNo);
    searchQuery.setCalled_no(rcvNo);
    searchQuery.setDn(vno);
    searchQuery.setSearch_period_from(periodFrom);
    searchQuery.setSearch_period_to(periodTo);
    searchQuery.setPageSize(result);
    searchQuery.setPageNo(pageNo);
    searchQuery.setSearch_time_to(timeTo);
    searchQuery.setSearch_time_from(timeFrom);

    ApiCommonVo<ApiPaginationListVo<CallLogVO>> returnData = apiMainService.getCallLogListSetTime(userId, tokenKey, searchQuery);
    response.setStatus(returnData.getStatus_code());

    long endTime = System.currentTimeMillis();

    map.put("time", ( endTime - startTime )/1000.0f +"초");
    mainDao.insertTimeMeasure(map);

    Map<String,Integer> enablePatchcall = adminDao.getEnablePatchCallSKB();
    supplyDao.skbUseNumber(enablePatchcall);

    return returnData;
  }
  
  /**
   * 교환기 상태 조회
   * @param response
   * @param tokenKey 토큰키
   * @return API 처리 결과 (true/false)
   */
  @ApiOperation(value="교환기 작동 상태 조회", notes="교환기 작동 상태를 조회합니다. 정상 작동시 info에 true, 비정상 작동시 info에 false값을 반환합니다.",
	        produces=MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value={@ApiResponse(code=200, message="성공적으로 조회했습니다."),
          @ApiResponse(code=400, message="입력 데이터 값의 형식이 유효하지 않습니다"),
          @ApiResponse(code=401, message="토큰 인증 실패"),
          @ApiResponse(code=405, message="해당 요청을 지원하지 않습니다"),
          @ApiResponse(code=406, message="해당 미디어 타입을 지원하지 않습니다 (application/json만 지원)"),
          @ApiResponse(code=500, message="해당 요청을 처리중 서버에서 오류가 발생하였습니다")})
  @RequestMapping(value="/ivr/status", method=RequestMethod.GET)
  public @ResponseBody ApiCommonVo<Boolean> getIvrStatus(HttpServletResponse response,
		  	@ApiParam(value="토큰 키", required=true)@RequestParam String tokenKey){
	  
	  return apiMainService.getIvrStatus(tokenKey);
  }


  /**
   * 사용자가 가지고 있는 토큰 리스트 조회
   * @return 사용자 토큰 정보 리스트
   */
  @ApiOperation(value = "토큰 리스트 조회", hidden=true)
  @RequestMapping(value="/token", method=RequestMethod.GET)
  public @ResponseBody List<ApiTokenVo> showUserTokenList(){
    UserVO userInfo = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return apiMainService.getUserTokenProtectInfoList(userInfo);
  }
  
  /**
   * 토큰 생성
   * @return 생성된 토큰키
   */
  //TODO 시큐리티에서 403에러가 나타나도록 설정해야됨
  @ApiOperation(value = "토큰 생성", hidden=true)
  @RequestMapping(value="/token", method=RequestMethod.POST)
  public @ResponseBody String genToken(){
    UserVO userInfo = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ApiTokenVo tokenInfo = apiMainService.generateToken(userInfo);
    
    return tokenInfo.getToken_key();
  }
  
  @ApiOperation(value = "메시지 토큰 생성", hidden=true)
  @RequestMapping(value="/messageToken", method=RequestMethod.POST)
  public @ResponseBody String genMessageToken(){
    UserVO userInfo = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ApiTokenVo tokenInfo = apiMainService.generateMessageToken(userInfo);
    
    return tokenInfo.getToken_key();
  }
  
  
  /**
   * 토큰 수정
   * @return 수정 여부
   */
  @ApiOperation(value = "토큰 수정", hidden=true)
  @RequestMapping(value="/token/{tokenKey}",method=RequestMethod.PUT)
  public @ResponseBody boolean editToken(@PathVariable("tokenKey") String tokenKey,
                    @RequestParam(required=false) String expirationDate,
                    @RequestParam(required=false) boolean tokenEnable){
    UserVO userInfo = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ApiTokenVo editInfo = new ApiTokenVo();
    editInfo.setToken_key(tokenKey);
    editInfo.setToken_expiration_date(expirationDate);
    editInfo.setToken_enable_yn(tokenEnable);
    
    if(apiMainService.editTokenInfo(editInfo, userInfo)){
      return true;
    } else {
      return false;
    }
  }

}