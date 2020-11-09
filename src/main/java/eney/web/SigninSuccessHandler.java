package eney.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SigninSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {


        System.out.println("444");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        String data = StringUtils.join(new String[]{" { \"response\" : {", " \"error\" : false , ", " \"message\" : \"로그인하였습니다.\" ", "} } "});
        PrintWriter out = response.getWriter();
        out.print(data);
        out.flush();
        out.close();
    }


}