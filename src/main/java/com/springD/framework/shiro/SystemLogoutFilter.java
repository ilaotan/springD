package com.springD.framework.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import com.springD.framework.config.StaticConstants;
import com.springD.framework.utils.StringUtils;

/**
 * user filter 主要为了控制不同类型用户的跳转
 * @author Chenz
 * @date 2014-11-6 16:32:59
 *
 */
public class SystemLogoutFilter extends LogoutFilter {
	/**
	 * 返回URL
	 */
	public static final String RETURN_URL = "returnUrl";

	protected String getRedirectUrl(ServletRequest req, ServletResponse resp,Subject subject) {
		HttpServletRequest request = (HttpServletRequest) req;
		String redirectUrl = request.getParameter(RETURN_URL);
		if (StringUtils.isBlank(redirectUrl)) {
			String type = request.getParameter("type");
//			if (request.getRequestURI().startsWith(request.getContextPath() + Constants.ADMIN_PREFIX)) {
			if(!StringUtils.isBlank(type) && type.equals("system")){
				redirectUrl = StaticConstants.ADMIN_LOGIN_URL;
			}else {
				redirectUrl = getRedirectUrl();
			}
		}
		return redirectUrl;
	}


	
}
