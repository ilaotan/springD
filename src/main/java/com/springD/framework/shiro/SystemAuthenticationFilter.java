package com.springD.framework.shiro;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.springD.framework.config.StaticConstants;
import com.springD.framework.exception.CaptchaErrorException;
import com.springD.framework.exception.CaptchaRequiredException;
import com.springD.framework.utils.ResponseUtils;
import com.springD.framework.utils.StringUtils;
import com.springD.framework.utils.UserUtils;



/**   
 * @Description: 扩展登录拦截器
 * @author Chenz
 * @date Nov 13, 2014 1:09:18 PM 
 * @version V1.0   
*/
public class SystemAuthenticationFilter extends FormAuthenticationFilter{

	private Logger logger = LoggerFactory.getLogger(SystemAuthenticationFilter.class);
	
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	
	/**
	 * 返回URL
	 */
	public static final String RETURN_URL = "returnUrl";
	
	private Cache<String, AtomicInteger> passwordRetryCache;

    public SystemAuthenticationFilter(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }
	
	
	protected boolean executeLogin(ServletRequest request,ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		if (token == null) {
			String msg = "create AuthenticationToken error";
			throw new IllegalStateException(msg);
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = (String) token.getPrincipal();
		boolean adminLogin=false;
		//判断是否管理员登录
		if (req.getRequestURI().startsWith(req.getContextPath() + StaticConstants.ADMIN_PREFIX)){
			adminLogin=true;
		}
		//验证码校验
		if (isCaptchaRequired(username,req, res)) {
			String captcha = request.getParameter(StaticConstants.CAPTCHA_PARAM);
			if (StringUtils.isNoneBlank(captcha)) {
				if (!imageCaptchaService.validateResponseForID(req.getSession().getId(), captcha)) {
					return onLoginFailure(token,adminLogin,new CaptchaErrorException(), request, response);
				}
			} else {
				return onLoginFailure(token,adminLogin,new CaptchaRequiredException(),request, response);
			}
		}
//		if(isDisabled(username)){
//			return onLoginFailure(token,adminLogin,new DisabledException(),request, response);
//		}
		try {
			Subject subject = getSubject(request, response);
			subject.login(token);
			return onLoginSuccess(token,adminLogin,subject, request, response);
		} catch (AuthenticationException e) {
			logger.info("", e);
			return onLoginFailure(token,adminLogin, e, request, response);
		}
	}
	
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		boolean isAllowed = isAccessAllowed(request, response, mappedValue);
		//登录跳转
		if (isAllowed && isLoginRequest(request, response)) {
			try {
				issueSuccessRedirect(request, response);
			} catch (Exception e) {
				logger.error("", e);
			}
			return false;
		}
		return isAllowed || onAccessDenied(request, response, mappedValue);
	}
	
	/**
	 * 登录成功后URL跳转设置
	 */
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String successUrl = req.getParameter(RETURN_URL);
		if (StringUtils.isBlank(successUrl)) {
			if (req.getRequestURI().startsWith(req.getContextPath() + StaticConstants.ADMIN_PREFIX)) {
				// 后台直接返回首页
//				successUrl = getAdminIndex();
				// 清除SavedRequest
				WebUtils.getAndClearSavedRequest(request);
				successUrl = StaticConstants.ADMIN_INDEX;
//				WebUtils.issueRedirect(request, response, successUrl, null,true);
//				return;
			} else if (req.getRequestURI().startsWith(req.getContextPath() + StaticConstants.CONTRACTOR_PREFIX)) {
				// 后台直接返回首页
				// 清除SavedRequest
				WebUtils.getAndClearSavedRequest(request);
				successUrl = StaticConstants.CONTRACTOR_INDEX;
			} else {
//				successUrl = getSuccessUrl();//
				// 清除SavedRequest
				WebUtils.getAndClearSavedRequest(request);
				successUrl = StaticConstants.KINDER_INDEX;
			}
		}
//		WebUtils.redirectToSavedRequest(req, res, successUrl);
		
		if(req.getHeader("accept").indexOf("application/json") > -1 || (req
				.getHeader("X-Requested-With")!= null && req
				.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)){
			//处理Ajax登录
			Map<String, Object> resultMap = new HashMap<String, Object>();
	        resultMap.put("success", true);
	        resultMap.put("successUrl", successUrl);
	        ResponseUtils.renderJson(res, resultMap);
	        return;
		}else{
			//普通请求
			WebUtils.redirectToSavedRequest(req, res, successUrl);
		}
		
	}
	
	/**
	 * 是否需要校验验证码
	 * @param username
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean isCaptchaRequired(String username,HttpServletRequest request, HttpServletResponse response) {
//		AtomicInteger retryCount = passwordRetryCache.get(username);
		AtomicInteger retryCount = passwordRetryCache.get(request.getSession().getId());
		String captcha = request.getParameter(StaticConstants.CAPTCHA_PARAM);
		// 如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
		if (!StringUtils.isBlank(captcha)|| (retryCount != null && retryCount.get() > StaticConstants.LOGIN_TRY_TIME)) {
			return true;
		}
		return false;
	}

	protected boolean isLoginRequest(ServletRequest req, ServletResponse resp) {
		return pathsMatch(getLoginUrl(), req)
			|| pathsMatch(StaticConstants.ADMIN_LOGIN_URL, req)
			|| pathsMatch(StaticConstants.CONTRACTOR_LOGIN_URL, req);
	}
	/**
	 * 登录成功
	 */
	private boolean onLoginSuccess(AuthenticationToken token,boolean adminLogin,Subject subject,
			ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		ShiroUser user = (ShiroUser) subject.getPrincipal();
		//登陆成功后的一些日志操作
//		CmsUser user = cmsUserMng.findByUsername(username);
//		String ip = RequestUtils.getIpAddr(req);
//		userMng.updateLoginInfo(user.getId(), ip);
//		//管理登录
//		if(adminLogin){
//			cmsLogMng.loginSuccess(req, user);
//		}
		// 清除需要验证码cookie
//		passwordRetryCache.remove(user.getName());
	
		//为druid的session监控 添加当前登录用户的用户名
		String name = UserUtils.getShiroUser().getName();
		setAttribute(req, res, StaticConstants.LOGIN_USERNAME,name);
		
		passwordRetryCache.remove(req.getSession().getId());
		return super.onLoginSuccess(token, subject, request, response);
	}
	private void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, String value) {
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}
	
	/**
	 * 登录失败
	 */
	private boolean onLoginFailure(AuthenticationToken token,boolean adminLogin,AuthenticationException e, ServletRequest request,
			ServletResponse response) {
//		String username = (String) token.getPrincipal();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
//		AtomicInteger retryCount = passwordRetryCache.get(username);
		AtomicInteger retryCount = passwordRetryCache.get(req.getSession().getId());
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
//            passwordRetryCache.put(username, retryCount);
            passwordRetryCache.put(req.getSession().getId(), retryCount);
        }
        retryCount.incrementAndGet();
		//管理登录
//		if(adminLogin){
//			cmsLogMng.loginFailure(req,"username=" + username);
//		}
        //如果是Ajax请求
        if(req.getHeader("accept").indexOf("application/json") > -1 || (req
					.getHeader("X-Requested-With")!= null && req
					.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)){
        	String exceptionName = e.getClass().getSimpleName();
        	Map<String, Object> resultMap = new HashMap<String, Object>();
        	resultMap.put("success", false);
        	resultMap.put("isCaptchaRequired",false);
        	if(retryCount.get() > StaticConstants.LOGIN_TRY_TIME){
        		resultMap.put("isCaptchaRequired",true);
        	}
        	if("DisabledAccountException".equals(exceptionName)){
        		resultMap.put("message", "如果您是家长,请到<a href='http://www.aiwei365.com'>爱维宝贝官网</a>登陆/激活<br/>如果您是园长,请使用激活时使用的用户名登陆");
    			resultMap.put("type","username");
        	}
        	else if("UnknownAccountException".equals(exceptionName)){
    			resultMap.put("message", "您输入的账户名和密码不匹配，请重新输入");
    			resultMap.put("type","username");
        	}else if("IncorrectCredentialsException".equals(exceptionName)){
    			resultMap.put("message", "您输入的账户名和密码不匹配，请重新输入");
    			resultMap.put("type","password");
        	}else if("CaptchaErrorException".equals(exceptionName)){
    			resultMap.put("message", "验证码错误");
    			resultMap.put("type","captcha");
        	}else if("CaptchaRequiredException".equals(exceptionName)){
    			resultMap.put("message", "验证码不能为空");
    			resultMap.put("type","captcha");
        	}else if("LockedAccountException".equals(exceptionName)){
    			resultMap.put("message", "账号被锁定或未激活");
    			resultMap.put("type","system");
        	}else if("ExcessiveAttemptsException".equals(exceptionName)){
    			resultMap.put("message", "错误次数超过限制");
    			resultMap.put("type","system");
        	}else{
    			resultMap.put("message", "未知错误，请联系管理员");
    			resultMap.put("type","system");
        	}
        	ResponseUtils.renderJson(res, resultMap);
        }
        setFailureAttribute(request, e);
//		return super.onLoginFailure(token, e, request, response);
        return false;
	}
	
	
}
