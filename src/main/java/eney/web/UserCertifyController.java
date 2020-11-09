package eney.web;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eney.domain.MobilUserCertifyResponseVo;
import eney.domain.UserCertifyVo;
import eney.domain.UserPrincipal;
import eney.domain.UserVO;
import eney.domain.payload.LoginRequest;
import eney.service.JoinService;
import eney.service.UserService;
import eney.util.EncryptUtil;
import eney.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import eney.exception.UserCertifyException;
import eney.service.UserCertifyService;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.http.protocol.HTTP.USER_AGENT;


@Controller
@RequestMapping("/userCertify")
public class UserCertifyController {
	
	@Autowired
	private UserCertifyService userCertifyService;
	@Autowired
	private JoinService joinService;
	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	/* 사용자 인증 처리 영역 */
	@RequestMapping(value="/ajax/getUserCertifyTradeid")
	public @ResponseBody String getUserCertifyTradeid(UserCertifyVo.UserCertifyType type){
		String tradeid = userCertifyService.makeUserCerity(type).getTradeid();
		
		return tradeid;
	}
	
	@RequestMapping(value="/return")
	public String userCertifyResponseRetrun(MobilUserCertifyResponseVo certifyResponse, Model model, HttpSession session, HttpServletRequest request){
		try {
			System.out.println("성공");
			MobilUserCertifyResponseVo decodeCertify = userCertifyService.decodeAndCheckForgery(certifyResponse);
			UserCertifyVo userCertify = userCertifyService.updateUserCerity(decodeCertify);

			model.addAttribute("ceritfyType", userCertify.getCeritfy_type());
			session.setAttribute("userCertify", userCertify);
			
		} catch (UserCertifyException e) {
			System.out.println("실패");
			model.addAttribute("error", true);
			e.printStackTrace();
		}

		return "redirect:/user/join";
	}

	@RequestMapping(value="/authCheck", method= RequestMethod.POST)
	public ResponseEntity<?> checkCertification(@RequestBody Map<String, String> params, Authentication authentication){

		JsonObject object = certificationProcess(params.get("impUid"));

		String ci = object.get("unique_key").getAsString();
		String di = object.get("unique_in_site").getAsString();
		String gender = object.get("gender").getAsString();
		String phone = object.get("phone").getAsString();
		String carrier = object.get("carrier").getAsString();
		String name = object.get("name").getAsString();
		String birthday = object.get("birthday").getAsString();

		UserCertifyVo.UserCertifyType certifyType = null;


		//디비에 ci있음
//		if(!joinService.checkUserCi(ci)){

			switch (params.get("type")){
				case "ID_FIND":
					certifyType = UserCertifyVo.UserCertifyType.ID_FIND;
					break;
				case "PW_RECOVER":
					certifyType = UserCertifyVo.UserCertifyType.PW_RECOVER;
					break;
				case "PHONE_CHANGE":
					certifyType = UserCertifyVo.UserCertifyType.PHONE_CHANGE;
					break;
				case "JOIN":
					certifyType = UserCertifyVo.UserCertifyType.JOIN;
					break;
			}

			if(certifyType.equals(UserCertifyVo.UserCertifyType.PHONE_CHANGE)){
				UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
				if(!user.getUsername().equals(name)){
					return new ResponseEntity<>("가입자와 인증정보가 일치하지않습니다",HttpStatus.BAD_REQUEST);
				}
			}else if(certifyType.equals(UserCertifyVo.UserCertifyType.JOIN) && !joinService.checkUserCi(ci)){
				return new ResponseEntity<>("이미 존재하는 회원입니다",HttpStatus.BAD_REQUEST);

			}

			UserCertifyVo userCertify = new UserCertifyVo();
			userCertify.setCeritfy_status(UserCertifyVo.UserCertifyStatus.success);
			userCertify.setCi(ci);
			userCertify.setDi(di);
			userCertify.setSex(gender);
			userCertify.setCeritfy_type(certifyType);
			userCertify.setName(name);
			userCertify.setPhone_number(phone);
			userCertify.setComm_id(carrier);


			userCertifyService.insertUserCerity(userCertify);

			HashMap<String,String> map = new HashMap<>();
			map.put("phone", phone);
			map.put("name", name);
			map.put("birthday", birthday);
			map.put("ci", ci);
			map.put("di", di);

			return new ResponseEntity<>(map,HttpStatus.OK);

	}

	@RequestMapping(value="/idFind", method= RequestMethod.POST)
	public ResponseEntity<?> idFind(@RequestBody Map<String, String> params){

		JsonObject object = certificationProcess(params.get("impUid"));

		String ci = object.get("unique_key").getAsString();

		UserVO user = joinService.getUserByCi(ci);

		if(user != null){
			return new ResponseEntity<>(user.getUserid(), HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value="/pwFind", method= RequestMethod.POST)
	public ResponseEntity<?> pwFind(@RequestBody Map<String, String> params){

		JsonObject object = certificationProcess(params.get("impUid"));

		String ci = object.get("unique_key").getAsString();
		String userId = params.get("userId");


		UserVO user = joinService.getUserByCi(ci);

		if(user.getUserid().equals(userId)){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>("아이디와 인증정보가 일치하지않습니다",HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="/pwCheck", method= RequestMethod.POST)
	public ResponseEntity<?> pwCheck(@RequestBody LoginRequest loginRequest, Authentication authentication) throws NoSuchAlgorithmException {


		if(loginRequest.getPassword() == null){
			return new ResponseEntity<>("패스워드를 입력하세요",HttpStatus.BAD_REQUEST);
		}

		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();


		UserVO userVO = userService.loadUserByUsername(user.getUserId());

		try{
			System.out.println(loginRequest.getPassword());
			System.out.println(user);
			System.out.println(userVO);
			System.out.println(userVO.getPassword());
			System.out.println(EncryptUtil.encryptSHA256(loginRequest.getPassword()));

			if(userVO.getPassword().equals(EncryptUtil.encryptSHA256(loginRequest.getPassword()))){
				return new ResponseEntity<>(HttpStatus.OK);
			}else{
				return new ResponseEntity<>("패스워드가 일치하지 않습니다",HttpStatus.BAD_REQUEST);
			}

		}catch(NoSuchAlgorithmException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
//		if(userVO.getPassword().equals(EncryptUtil.encryptSHA256(password))){
//			return new ResponseEntity<>(HttpStatus.OK);
//		}else{
//			return new ResponseEntity<>("패스워드가 일치하지 않습니다",HttpStatus.BAD_REQUEST);
//		}
	}

	//사용자 인증 처리
//	@RequestMapping(value="/", method= RequestMethod.POST)
	public JsonObject certificationProcess(String impUid){
		String requestURL = "https://api.iamport.kr/certifications";

		String token = "Bearer ";
		token += getCertifyAccessToken();
		String res = (String) HttpRequestUtil.sendHttpGet(requestURL, impUid,token,null).get("body");;
		System.out.println(impUid);
		System.out.println(res);

		JsonParser jsonParser = new JsonParser();
		JsonObject object = (JsonObject)jsonParser.parse(res);

		JsonObject certifyInfo = (JsonObject)object.get("response");
		String ci = certifyInfo.get("unique_key").getAsString();

		System.out.println(object);
		System.out.println(ci);

//		joinService.checkUserCi(ci);
		return certifyInfo;

	}

	public String getCertifyAccessToken() {
		String token = "";

		String requestURL = "https://api.iamport.kr/users/getToken";
		String impKey = "4781346965264826";
		String impSecret = "rVJkqCVRlI1JaGFPcWqGNJiA7GIFjcyK8h4zU0rgJs0T9FImC93qTm003JGmVf74wcDrc4HVxHOHnx40";

		try{
			JsonObject json = new JsonObject();
			json.addProperty("imp_key", impKey);
			json.addProperty("imp_secret", impSecret);

			String res = (String) HttpRequestUtil.sendHttpPost(requestURL, json, null,null).get("body");

			JsonParser jsonParser = new JsonParser();
			JsonObject object = (JsonObject)jsonParser.parse(res);
			JsonObject response = (JsonObject)object.get("response");
			token = response.get("access_token").toString().replace("\"","");

		}catch(Exception e){
			e.printStackTrace();
		}

		return token;

	}

}
