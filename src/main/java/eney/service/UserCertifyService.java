package eney.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import eney.domain.MobilUserCertifyRequestVo;
import eney.domain.MobilUserCertifyResponseVo;
import eney.domain.UserCertifyVo;
import eney.exception.UserCertifyException;
import eney.property.EneyProperties;
import eney.property.PortalProperties;
import eney.property.UserCertifyProperties;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eney.domain.MobilUserCertifyRequestVo;
import eney.domain.MobilUserCertifyResponseVo;
import eney.domain.UserCertifyVo;
import eney.domain.UserCertifyVo.UserCertifyStatus;
import eney.domain.UserCertifyVo.UserCertifyType;
import eney.exception.UserCertifyException;
import eney.mapper.UserDao;
import eney.property.EneyProperties;
import eney.property.PortalProperties;
import eney.property.UserCertifyProperties;

import mup.mcash.module.common.McashCipher;

@Service
public class UserCertifyService {
	
	@Autowired
	private UserDao userDao;
	
	private PortalProperties portalProperties;
	private UserCertifyProperties userCertifyProperties;
	
	public MobilUserCertifyRequestVo getUserCerifyRequestData(){
		MobilUserCertifyRequestVo userCertifyRequest = new MobilUserCertifyRequestVo();
		
		userCertifyRequest.setCashGb(userCertifyProperties.getCashGb());
		userCertifyRequest.setPayMode(userCertifyProperties.getPayMode());
		userCertifyRequest.setSiteurl(portalProperties.getPortalDomain());
		userCertifyRequest.setCiSvcid(userCertifyProperties.getCiSvcid());
		userCertifyRequest.setCiMode(userCertifyProperties.getCiMode());
		userCertifyRequest.setOkurl(userCertifyProperties.getOkurl());
		userCertifyRequest.setCryptyn(userCertifyProperties.getCryptyn());
		userCertifyRequest.setKeygb(userCertifyProperties.getKeygb());
		userCertifyRequest.setLogoYn(userCertifyProperties.getLogoYn());
		userCertifyRequest.setCallType(userCertifyProperties.getCallType());
		
		return userCertifyRequest;
	}
	
	/**
	 * 사용자 인증 정보 생성 (tradeid 생성 목적)
	 * @param type
	 * @return
	 */
	public UserCertifyVo makeUserCerity(UserCertifyVo.UserCertifyType type) {
		UserCertifyVo userCertify = new UserCertifyVo();
		
		userCertify.setCeritfy_status(UserCertifyVo.UserCertifyStatus.stanby);
		userCertify.setCeritfy_type(type);
		userDao.insertUserCerity(userCertify);
		
		userCertify = getUserCerity(userCertify.getUser_cretify_idx());
		
		return userCertify;
	}

	public void insertUserCerity(UserCertifyVo userCertify) {
//		UserCertifyVo userCertify = new UserCertifyVo();
		userDao.insertUserCerity(userCertify);
	};

	public UserCertifyVo updateUserCerity(MobilUserCertifyResponseVo certify){
		String tradeid = certify.getTradeid();
		int user_cretify_idx = Integer.parseInt(tradeid.substring(8, tradeid.length()));
		UserCertifyVo userCertify = getUserCerity(user_cretify_idx);
		
		if(UserCertifyVo.UserCertifyStatus.stanby.equals(userCertify.getCeritfy_status())){
			if(MobilUserCertifyResponseVo.SUCCESS_RESULTCD.equals(certify.getResultcd())){
				userCertify.setCeritfy_status(UserCertifyVo.UserCertifyStatus.success);
			} else {
				userCertify.setCeritfy_status(UserCertifyVo.UserCertifyStatus.error);
			}
			
			userCertify.setCi(certify.getCi());
			userCertify.setDi(certify.getDi());
			userCertify.setMobil_id(certify.getMobilid());
			userCertify.setSign_date(certify.getSigndate());
//			userCertify.setName(certify.getName());
			userCertify.setName(certify.getName2());			// 인코딩 문제로 암호화 되지 않은 이름 사용
			userCertify.setPhone_number(certify.getNo());
			userCertify.setComm_id(certify.getCommid());
			userCertify.setSocialno(certify.getSocialno());
			userCertify.setSex(certify.getSex());
			userCertify.setForeigner(certify.getForeigner());
			userCertify.setMstr(certify.getMSTR());
			userCertify.setResult_cd(certify.getResultcd());
			userCertify.setResult_msg(certify.getResultmsg());
			
			userDao.updateUserCertify(userCertify);
		}
		
		return userCertify;
	}

	/**
	 * KG 모빌리언스에서 받은 사용자 인증 정보를 복조화와 위변조 여부를 확인
	 * @param certify KG 모빌리언스에서 받은 사용자 인증 정보
	 * @return 복조화된 사용자 인증 정보
	 * @throws UserCertifyException 사용자 인증 오류 (위변조)
	 */
	public MobilUserCertifyResponseVo decodeAndCheckForgery(MobilUserCertifyResponseVo certify) throws UserCertifyException{
		decodeUserCerifyResponse(certify);
		
		if(checkForgery(certify)){
			throw new UserCertifyException("데이터가 위·변조되었습니다");
		}
		
		return certify;
	}
	
	/**
	 * DB에 저장된 UserCerity 조회 + tradeid 생성
	 * @param user_cretify_idx 조회할 UserCerity의 idx
	 * @return 조회된 데이터
	 */
	public UserCertifyVo getUserCerity(int user_cretify_idx){
		UserCertifyVo userCerity = userDao.getUserCerityByUser_cretify_idx(user_cretify_idx);
		String tradeid = new SimpleDateFormat("yyyyMMdd").format(userCerity.getTimestamp()) 
						+ userCerity.getUser_cretify_idx();
		
		userCerity.setTradeid(tradeid);
		
		return userCerity;
	}
	
	/**
	 * 사용자 인증 데이터 복조화
	 * @param certify 암호화된 사용자 인증 데이터
	 * @return 복조화된 사용자 인증 데이터
	 */
	private MobilUserCertifyResponseVo decodeUserCerifyResponse(MobilUserCertifyResponseVo certify){
		String decodeKey ;
		
		if("0".equals(userCertifyProperties.getKeygb())){
			/* 고유KEY 미 설정 시 비밀키 : "CI_SVCID 앞 8자리 + CI_SVCID 앞 8자리" (16byte) keygb 0 */
			decodeKey = userCertifyProperties.getCiSvcid().substring(0,8) 
					+ userCertifyProperties.getCiSvcid().substring(0, 8);
		} else {
			/* 고유KEY 설정 시 비밀키 : "가맹점 고유 KEY + CI_SVCID 앞 8자리" (16byte) Keygb 1 or 2 */
			decodeKey = userCertifyProperties.getMemberCustomEncKey().substring(0,8) 
					+ userCertifyProperties.getCiSvcid().substring(0, 8);
		}

		/* 서버의 UTF-8과 Response의 EUC-KR 인코딩 문제 처리 */
		certify.setName(McashCipher.decodeString(certify.getName(), decodeKey));
		
//		name2

		certify.setNo(McashCipher.decodeString(certify.getNo(), decodeKey));
		certify.setCommid(McashCipher.decodeString(certify.getCommid(), decodeKey));
		certify.setSocialno(McashCipher.decodeString(certify.getSocialno(), decodeKey));
		certify.setSex(McashCipher.decodeString(certify.getSex(), decodeKey));
		certify.setForeigner(McashCipher.decodeString(certify.getForeigner(), decodeKey));
		certify.setCi(McashCipher.decodeString(certify.getCi(), decodeKey));
		certify.setDi(McashCipher.decodeString(certify.getDi(), decodeKey));
		
		return certify;
	}
	
	/**
	 * 사용자 인증 데이터 위변조 여부 확인
	 * @param certify 전달받은 사용자 인증 데이터
	 * @return 위변조 여부
	 */
	private boolean checkForgery(MobilUserCertifyResponseVo certify){
		try {
			String key = certify.getSigndate() + certify.getDi() + certify.getCi() 
						+ certify.getMobilid() + certify.getSvcid().substring(0, 8) 
						+ certify.getSvcid().substring(0, 8);
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			
			sha256.update(key.getBytes());
			byte[] byteData = sha256.digest();
			
			String sha = Hex.encodeHexString(byteData);
			
			return !certify.getMac().equals(sha);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	@Autowired
	public void setPortalProperties(EneyProperties eneyProperties){
		this.portalProperties = eneyProperties.getPortal();
	}
	
	@Autowired
	public void setUserCertifyProperties(EneyProperties eneyProperties){
		this.userCertifyProperties = eneyProperties.getPortal().getUserCertifyProperties();
	}
}
