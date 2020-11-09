package eney;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import eney.property.EneyProperties;

@Configuration
public class CommonConfig {
	
	@Autowired
	private EneyProperties eneyProperties;
	
	@Bean
	public JavaMailSenderImpl javaMailSenderImplBean(){
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		
		javaMailSenderImpl.setHost(eneyProperties.getPortal().getMailHost());
		javaMailSenderImpl.setPort(eneyProperties.getPortal().getMailPort());
		javaMailSenderImpl.setDefaultEncoding("UTF-8");
		
		return javaMailSenderImpl;
	}
	
	
}
