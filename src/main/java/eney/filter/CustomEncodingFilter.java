package eney.filter;

import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomEncodingFilter extends OrderedCharacterEncodingFilter{
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String encoding = isEuckrFilter(request) ? "EUC-KR" : getEncoding();
		if (encoding != null) {
			if (isForceRequestEncoding() || request.getCharacterEncoding() == null) {
				request.setCharacterEncoding(encoding);
			}
			if (isForceResponseEncoding()) {
				response.setCharacterEncoding(encoding);
			}
		}
		filterChain.doFilter(request, response);
	}
	
	protected boolean isEuckrFilter(HttpServletRequest req){
		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getRequestURI().substring(request.getContextPath().length()).trim();
		
		if(path.startsWith("/userCertify/return") || path.startsWith("/payment/")){
			return true;
		}
		
		return false;
	}
}
