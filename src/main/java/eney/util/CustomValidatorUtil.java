package eney.util;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.sun.org.apache.xpath.internal.operations.Bool;
import eney.domain.UserVO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import eney.domain.UserVO;
import eney.mapper.UserDao;
import eney.service.UserService;

@Component
public class CustomValidatorUtil {
	
	@Resource
	UserService userService;
	
	@Resource
	UserDao userDao;

	public void rejectIfNotUseridFormed(Errors errors, String userid) {
		String regex = "^[a-z0-9]{3,12}$";
		if(!Pattern.matches(regex, userid))
			errors.rejectValue("userid", "field.required.form");
	}

	public void rejectIfOverlappedUserid(Errors errors, String userid) {
		if(userService.loadUserByUsername(userid) != null 
				|| userService.loadDropUserByUsername(userid) != null)
			errors.rejectValue("userid", "field.existed.value");
	}

	public void rejectIfNotPasswordFormed(Errors errors, String password) {
		String regex = "([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])";
		
		if(password.length()<8){
			errors.rejectValue("password", "field.required.form");
			return;
		}
		if(!Pattern.matches(regex, password)){
			errors.rejectValue("password", "field.required.form");
			return;
		}
		
	}

	public void rejectIfPasswordNotFormedOrNotEqual(Errors errors, String password, String password_check) {
		if(!(password.equals(password_check)))
			errors.rejectValue("password_check", "field.notEqual.password");
		else if(userService.checkPasswordRegex(password)!= true){
			errors.rejectValue("password_check", "field.required.form");
		}
	}

	public Boolean passwordNotFormedOrNotEqual(String password) {
		if(userService.checkPasswordValidation(password)){
			return true;
		}else{
			return false;
		}
	}

	public Boolean useridNotFormed(String userid) {

		String pattern = "^[a-z0-9]{3,12}$";
		if(userid.matches(pattern)) {
			return true;
		}else{
			return false;
		}
	}

	public void rejectIfNotEmailFormedOrOverlapped(Errors errors, String email) {
		String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		if(!Pattern.matches(regex, email)){
			errors.rejectValue("email", "field.required.form");
		}else{
			UserVO userVO = new UserVO();
			userVO.setColumn("email");
			userVO.setColumn_data(email);
			if(userDao.getUserInfoByData(userVO)!=null){
				errors.rejectValue("email", "field.existed.value");
			}
		}
			
	}
	
	public void rejectIfNotPhoneNumberFormed(Errors errors, String phone_num) {
		//String regex = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
		phone_num = phone_num.replace("-", "");
		String regex = "^\\d{2,4}\\d{3,4}\\d{4}$";
		if(!Pattern.matches(regex, phone_num))
			errors.rejectValue("phone_num", "field.required.form");
	}

	public void rejectIfNotAccountNumberFormed(Errors errors, String account_number) {
//		String regex = "[0-9,\\-]{3,6}\\-[0-9,\\-]{2,6}\\-[0-9,\\-]";
//		if(!Pattern.matches(regex, account_number))
//			errors.rejectValue("account_number", "field.required.form");
		account_number = account_number.replace("-", "");
		try {
	        Double.parseDouble(account_number);
	        
	    } catch (NumberFormatException e) {
	    	errors.rejectValue("account_number", "field.required.form");
	    }
	}

	/**
	 * <p>XXX - XX - XXXXX 형식의 사업자번호 앞,중간, 뒤 문자열 3개 입력 받아 유효한 사업자번호인지 검사.</p>
	 *
	 *
	 * @param   3자리 사업자앞번호 문자열 , 2자리 사업자중간번호 문자열, 5자리 사업자뒷번호 문자열
	 * @return  유효한 사업자번호인지 여부 (True/False)
	 */
	public static boolean checkCompNumber(String corporateNumber) {


		int hap = 0;
		int temp = 0;
		int check[] = {1,3,7,1,3,7,1,3,5};  //사업자번호 유효성 체크 필요한 수

		if(corporateNumber.length() != 10)    //사업자번호의 길이가 맞는지를 확인한다.
			return false;

		for(int i=0; i < 9; i++){
			if(corporateNumber.charAt(i) < '0' || corporateNumber.charAt(i) > '9')  //숫자가 아닌 값이 들어왔는지를 확인한다.
				return false;

			hap = hap + (Character.getNumericValue(corporateNumber.charAt(i)) * check[temp]); //검증식 적용
			temp++;
		}

		hap += (Character.getNumericValue(corporateNumber.charAt(8))*5)/10;

		if ((10 - (hap%10))%10 == Character.getNumericValue(corporateNumber.charAt(9))) //마지막 유효숫자와 검증식을 통한 값의 비교
			return true;
		else
			return false;
	}

}
