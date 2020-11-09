// TODO 용도 확인 후 사용하는 곳이 없으면 삭제 예정

//package com.eney.portal.util;
//
//import java.util.Locale;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.support.MessageSourceAccessor;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CmsMessage {
//
//	/**
//	 * MessageSourceAccessor
//	 */
//	private static MessageSourceAccessor msAcc = null;
//
//	@Autowired
//	public void setMessageSourceAccessor(MessageSourceAccessor msAcc) {
//		CmsMessage.msAcc = msAcc;
//	}
//
//	/**
//	 * KEY에 해당하는 메세지 반환
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public static String getMessage(String key) {
//		return msAcc.getMessage(key, Locale.getDefault());
//	}
//
//	/**
//	 * KEY에 해당하는 메세지 반환
//	 * 
//	 * @param key
//	 * @param objs
//	 * @return
//	 */
//	public static String getMessage(String key, Object[] objs) {
//		return msAcc.getMessage(key, objs, Locale.getDefault());
//	}
//}