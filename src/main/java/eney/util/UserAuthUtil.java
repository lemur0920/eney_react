package eney.util;

import java.util.Collection;

import eney.domain.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuthUtil {
	/**
	 * 사용자 권한 확인
	 * @param authString 확인할 권한
	 * @param userInfo 사용자 정보
	 * @return 권한 여부
	 */
	public static Boolean isAuthCheck(String authString, UserVO userInfo){
		boolean authCheck  = false;
		@SuppressWarnings("unchecked")
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		for (SimpleGrantedAuthority authoritity : authorities) {
			if(String.valueOf(authoritity).equals(authString)){
				authCheck = true;
			}
		}
		return authCheck;
	}

	//권한을 가지고 있는지 확인
	public static String getUserRole(Collection<GrantedAuthority> authorities) {
		String role = "";
		for (GrantedAuthority grantedAuthority : authorities) {
			if(grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
				role = "ADMIN";
				break;
			}else if(grantedAuthority.getAuthority().equals("ROLE_PARTNER")){
				role = "PARTNER";
				break;
			}else{
				role = "USER";
				break;
			}
		}
		return role;
	}
}
