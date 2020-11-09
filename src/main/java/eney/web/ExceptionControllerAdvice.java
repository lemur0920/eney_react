package eney.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import eney.util.CustomDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import eney.service.MailService;
import eney.util.CustomDataAccessException;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@Resource
	MailService mailService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
	
    @ExceptionHandler(CustomDataAccessException.class)
    public void processCustomDataAccessException(CustomDataAccessException cdae){
    	Map<String,Object> mailModel = new HashMap<>();
    	String title = "SQL쿼리문 에러 발생";
    	String templet ="sqlErrorAlert";
    	String errorSql = cdae.getQuery();
    	mailModel.put("errorSql", errorSql);
    	Boolean isEmailSend = mailService.sendTempletMail(title, templet, mailModel);
    	
    	logger.info("SQL쿼리문 에러 메일 발송"
				+ ", isEmailSend: " + isEmailSend);
        
    }
}
