package eney.validator;

import javax.annotation.Resource;

import eney.domain.UserVO;
import eney.domain.payload.JoinReponse;
import eney.service.JoinService;
import eney.service.UserService;
import eney.util.CustomValidatorUtil;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class JoinValidator implements Validator {
	
	@Resource
    CustomValidatorUtil customValidatorUtil;

	@Autowired
	UserService userService;

	@Autowired
	JoinService joinService;
	
	/**
	 * 이 Validator는 단순히 UserVO 인스턴스를 유효성검사한다
	 */
	public boolean supports(Class<?> clazz) {
		return UserVO.class.equals(clazz);
	}
	
	/**
	 * 아이디 중복검사
	 * @param obj
	 * @param errors
	 */
	public void overlappedUserid(Object obj, Errors errors){
		UserVO info = (UserVO) obj;
		
		customValidatorUtil.rejectIfNotUseridFormed(errors, info.getUserid());
		customValidatorUtil.rejectIfOverlappedUserid(errors, info.getUserid());
	}
	
	public void validate(Object obj, Errors errors) {
		//ValidationUtils.rejectIfEmpty(e, "name", "name.empty");
		UserVO info = (UserVO) obj;
		System.out.println(info);
		//ValidationUtils.rejectIfEmpty(errors, "userid", "field.required.notEmpty");
		ValidationUtils.rejectIfEmpty(errors, "name", "field.required.notEmpty");
		customValidatorUtil.rejectIfNotUseridFormed(errors, info.getUserid());
		customValidatorUtil.rejectIfOverlappedUserid(errors, info.getUserid());
		//TODO 비밀번호 다시... 
		//customValidatorUtil.rejectIfNotPasswordFormed(errors, info.getPassword());
		customValidatorUtil.rejectIfPasswordNotFormedOrNotEqual(errors, info.getPassword(), info.getPassword_check());
		
		customValidatorUtil.rejectIfNotEmailFormedOrOverlapped(errors, info.getEmail_address());
		
		if(info.getMember_kind().equals("corporate")){
			customValidatorUtil.rejectIfNotPhoneNumberFormed(errors, info.getPhone_number());
		}else{
			ValidationUtils.rejectIfEmpty(errors, "birth_day", "field.required.notEmpty");
		}
		if(info.getMember_kind().equals("agent")){
			ValidationUtils.rejectIfEmpty(errors, "account_bank", "field.required.notEmpty");
			customValidatorUtil.rejectIfNotAccountNumberFormed(errors, info.getAccount_number());
		}
		
	}

	public JoinReponse userValidate(UserVO user) {
		JoinReponse joinReponse = new JoinReponse();
		EmailValidator emailValidator = EmailValidator.getInstance();

		if(!customValidatorUtil.useridNotFormed(user.getUserid())){
			joinReponse.setResult(false);
			joinReponse.setUserIdError("아이디는 최소 3~12자 입니다");
		}

		if(userService.loadUserByUsername(user.getUserid()) != null || userService.loadDropUserByUsername(user.getUserid()) != null){
			joinReponse.setResult(false);
			joinReponse.setUserIdError("이미 존재하는 아이디 입니다");
		}


		if(user.getCi() == null || user.getCi().equals("")){
			joinReponse.setResult(false);
			joinReponse.setCertifyError("본인 인증을 완료하세요");
		}else{
			if(!joinService.checkUserCi(user.getCi())){
				joinReponse.setResult(false);
				joinReponse.setCertifyError("이미 가입한 회원입니다");
			}
		}

		if(UserVO.MEMBER_KIND_CORPORATE.equals(user.getMember_kind())){
			if(!customValidatorUtil.checkCompNumber(user.getCorporate_number().replace("-", ""))){
				joinReponse.setResult(false);
				joinReponse.setCorporateError("유효한 사업자 번호가 아닙니다");
			}
		}

		if(!customValidatorUtil.passwordNotFormedOrNotEqual(user.getPassword()) || !user.getPassword().equals(user.getPassword_check())){
			joinReponse.setResult(false);
			joinReponse.setPasswordError("비밀번호 길이는 8~16자 이내로 반드시 숫자,문자,특수문자가 1개 이상 포함되어야 합니다");
		}

		if(!emailValidator.isValid(user.getEmail())){
			joinReponse.setResult(false);
			joinReponse.setEmailError("E-Mail을 확인하세요");
		}


		return joinReponse;
	}
}