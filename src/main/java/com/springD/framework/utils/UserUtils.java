package com.springD.framework.utils;

import com.springD.framework.common.Constants;
import com.springD.framework.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicInteger;




/**
 * 用户util
 * @author Chenz
 *
 */
public class UserUtils {
	

	public static final String CACHE_USER = "userCache";
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static ShiroUser getShiroUser(){
		Subject subject = SecurityUtils.getSubject();
		ShiroUser user = (ShiroUser) subject.getPrincipal();
		return user;
	}
	/**
	 * 判断是否需要验证码
	 * @param username
	 * @return
	 */
	public static boolean isCaptchaRequired(HttpSession session){
		String sessionId = session.getId();
		AtomicInteger retryCount = (AtomicInteger) CacheUtils.get("passwordRetryCache", sessionId);
		if(retryCount != null && retryCount.get() > Constants.LOGIN_TRY_TIME){
			return true;
		}else{
			return false;
		}
	}
}
