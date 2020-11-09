package eney.web;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import eney.domain.*;
import eney.util.AcsUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import eney.domain.AcsTransmitVO;
import eney.domain.UserVO;
import eney.service.AcsService;
import eney.service.UserService;
import eney.util.AcsUtils;

@Controller
@RequestMapping("/service/acs")
public class AcsController {
	
	@Resource
	AcsService acsService;
	
	@Resource
	UserService userService;

	/**
	 * 발신번호 체크
	 * @param mobile
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/number_check"  , method= RequestMethod.POST)
	public ResponseEntity<?> messagingCheckNumber(@RequestBody String mobile) throws IOException {
		List<AcsTransmitVO> numberCheck = acsService.getMessaingNumberList();
		String temp = "";

		temp = mobile.replaceAll("-", "");

		if(temp.matches("^(02|0[3-6]\\d|01(0|1|3|5|6|7|8|9)|070|080|007)-?\\d{3,4}-?\\d{4,5}$") ||
				temp.matches("^(15|16|18)\\d{2}-?\\d{4,5}$")){
			if(temp.matches("^(02|0[3-6]\\d|01(0|1|3|5|6|7|8|9)|070|080)-?0{3,4}-?\\d{4}$")) {
				return new ResponseEntity<>("사용 불가능한번호입니다. 다시 한 번 확인해주세요.",HttpStatus.BAD_REQUEST);
			}else{
				AcsTransmitVO acsVO = new AcsTransmitVO();
				acsVO.setTel_num(mobile);

				AcsTransmitVO acs = acsService.getMessagingDupleCheck(mobile);
				if(acs == null || acs.getStatus() == null || acs.getStatus().equals("I") ){
					return new ResponseEntity<>(HttpStatus.OK);
				}else{
					return new ResponseEntity<>("이미 이용중인 번호입니다",HttpStatus.BAD_REQUEST);
				}
			}
		}else{
			return new ResponseEntity<>("사용 불가능한번호입니다. 다시 한 번 확인해주세요.",HttpStatus.BAD_REQUEST);
		}
	}

	
	/**
	 * 발신번호 등록을 위한 ARS 인증
	 * @param acsVO 소켓 전송 메소드
	 * @param mobile 인증 할 휴대폰번호
	 */
	@ResponseBody
	@RequestMapping(value = "/acsSubmit", method= RequestMethod.POST)
	public ResponseEntity<?> AcsSubmit(@RequestBody String mobile, Authentication authentication) {
		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		UserVO userVO = userService.loadUserByUsername(user.getUserId());

		AcsTransmitVO acsVO = new AcsTransmitVO();
		setTransmitContent(acsVO);
		acsVO.setUserid(userVO.getUserid());
		AcsTransmitVO checkNumberforduple = acsService.getMessagingDupleCheck(mobile);

		System.out.println(mobile);
		
		if(checkNumberforduple != null){
			if(("I").equals(checkNumberforduple.getStatus()) || checkNumberforduple.getStatus() == null){
				acsVO.setTel_num(mobile);
				acsService.deleteMessagingNumber(acsVO);
				checkNumberforduple = acsService.getMessagingDupleCheck(mobile);
			}
		}
		
		//인터넷 발송문자 기술적 조치사항 참고
		if(mobile.matches("^(02|0[3-6]\\d|01(0|1|3|5|6|7|8|9)|070|080|007)-?\\d{3,4}-?\\d{4,5}$") 
				|| mobile.matches("^(15|16|18)\\d{2}-?\\d{4,5}$")){
			if(mobile.matches("^(02|0[3-6]\\d|01(0|1|3|5|6|7|8|9)|070|080)-?0{3,4}-?\\d{4}$")) {
				acsVO.setTel_num(null);
				return new ResponseEntity<>("사용불가능한 번호입니다",HttpStatus.CONFLICT);
			}else{
				if(checkNumberforduple == null){
					acsVO.setTel_num(AcsUtils.getRPad(mobile, 12, ' '));
				}else{
					return new ResponseEntity<>("사용불가능한 번호입니다",HttpStatus.CONFLICT);
				}
			}
		}else{
			return new ResponseEntity<>("사용불가능한 번호입니다",HttpStatus.CONFLICT);
		}
		
		acsService.SetTransmitPacket(acsVO);

        acsVO.setReq_num(acsVO.getReq_num());
        
        byte[] requestData = AcsUtils.converterByteArrayToAcsTransmit(acsVO);
        
        byte[] responseData = transfer(requestData);
        
        AcsTransmitVO response = AcsUtils.converterAcsResponseToByteArray(responseData);
        
        acsVO.setResult(response.getResult());
        acsService.UpdateAcsStatus(acsVO);
        
        acsVO.setStatus("I");
        acsService.UpdateAcsStatus(acsVO);
        return new ResponseEntity<>(acsVO, HttpStatus.OK);
        
	}
	
	@ResponseBody
	@RequestMapping("/checkAcsNumber")
	public ResponseEntity CheckAcsNumber(String acs_number,String req_num, HttpSession session) {
		
		AcsTransmitVO trasnmitVO = acsService.GetTransmitPacket(req_num);
		
		session.setAttribute("trasnmitVO", trasnmitVO);

		if(acs_number.equals(trasnmitVO.getAuth_num())){
			trasnmitVO.setStatus("A");
			acsService.UpdateAcsStatus(trasnmitVO);

			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>("발신번호등록 실패",HttpStatus.BAD_REQUEST);
		}
	}
	/**
	 * 소켓 연동을 위한 acsVO 설정(변경 금지)
	 * @param acsVO
	 * @return acsVO
	 */
	public AcsTransmitVO setTransmitContent(@ModelAttribute AcsTransmitVO acsVO){
		acsVO.setCode("1001"); //고정 0~3 !!!!!!!변경금지!!!!!!!
    	acsVO.setCompany_id("eney01"); //고정 4~13 !!!!!!!변경금지!!!!!!!
    	acsVO.setLength("0094"); //고정 14~17 !!!!!!!변경금지!!!!!!!
        //packet Identification length = 12 
        /*packetVO.setReq_num(getRPad("1" , 12 , ' '));*/ // 18~29 
    	acsVO.setMode("97"); //고정  30~31 !!!!!!!변경금지!!!!!!!
    	acsVO.setResult("00"); //고정 32~33 !!!!!!!변경금지!!!!!!!
    	 //통보 전화번호 length = 12
    	acsVO.setTel_num("");
        //발신번호  length = 12 !!!!!!!변경금지!!!!!!!
    	acsVO.setCall_back_num("07079331616"); //46~57
        //인증번호  length = 14
        // 1000~9999까지의 난수 생성
    	Random random = new Random();
    	int result = random.nextInt(10000)+1000;
    	if(result>10000){ result = result - 1000; }
        acsVO.setAuth_num(String.valueOf(result)); //58~71
        //결과를 받을 에네이측 서버 IP length = 16
        acsVO.setServer_ip(""); //72~87
        //결과를 받을 에네이측 서버 포트 length = 6
        acsVO.setServer_port(""); //88~93
    	
		return acsVO;
		
	}
	
	/**
	 * 소켓 연동(클라이언트)
	 * @param requestData
	 * @return
	 */
	public static byte[] transfer(byte[] requestData){
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("58.229.254.243", 9700));
			
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			
			dataOutputStream.write(requestData);
			
			byte[] responseData = new byte[46];
			dataInputStream.read(responseData);
			
			dataInputStream.close();
			dataOutputStream.close();
			inputStream.close();
			outputStream.close();
			socket.close();
			
			return responseData;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
