package com.springD.framework.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springD.framework.config.StaticConstants;

/**   
 * @Description: shiro权限拦截器，根据url判断权限
 * @author Chenz
 * @date Nov 13, 2014 1:09:56 PM 
 * @version V1.0   
*/
public class SystemPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
	
	private static final Logger log = LoggerFactory.getLogger(SystemPermissionsAuthorizationFilter.class);

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
		Subject user = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) user.getPrincipal();
		
		HttpServletRequest req = (HttpServletRequest) request;
		Subject subject = getSubject(request, response);
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		
		if(uri.endsWith("/json")){// 去掉json
			uri=uri.substring(0,uri.length()-5);
		}
		if(uri.endsWith(".do")){// 去掉more
			uri=uri.substring(0,uri.length()-3);
		}
		int i=uri.indexOf(contextPath);
		if(i>-1){
			uri=uri.substring(i+contextPath.length());
		}
		if(StringUtils.isBlank(uri)){
			uri="/";
		}
//		System.out.println();
		String permission = uri.replaceFirst("/", "").replaceAll("/", ":");
		boolean permitted = false;
		if("/".equals(uri)){
			permitted=true;
		}else if(null != shiroUser && null != shiroUser.getCode() && shiroUser.getCode().equals(StaticConstants.SUPER_ADMIN_CODE)){//如果是超管角色
			return true;
		}else{
			permitted= subject.isPermitted(permission);
		}
		return permitted;
	}
	
	//未登录重定向到登陆页
	protected void redirectToLogin(ServletRequest req, ServletResponse resp) throws IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String loginUrl;
		//后台地址跳转到后台登录地址，前台需要登录的跳转到shiro配置的登录地址
		if (request.getRequestURI().startsWith(request.getContextPath() + StaticConstants.ADMIN_PREFIX)) {
			loginUrl = StaticConstants.ADMIN_LOGIN_URL;
		} else {
			loginUrl = getLoginUrl();
		}
		WebUtils.issueRedirect(request, response, loginUrl);
	}	
	
}
