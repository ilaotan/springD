package com.springD.framework.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.springD.framework.utils.ResponseUtils;

/**   
 * @Description: 自定义异常处理，区分Ajax和普通请求
 * @author Chenz
 * @date Nov 13, 2014 1:07:23 PM 
 * @version V1.0   
*/
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver{

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String viewName = determineViewName(ex, request);
		if (viewName != null) {// JSP格式返回
			if (!(request.getHeader("accept").contains("application/json")|| (
					request.getHeader("X-Requested-With")!= null && request
					.getHeader("X-Requested-With").contains("XMLHttpRequest"))
				)) {
				// 如果不是异步请求
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {// JSON格式返回
//				try {
//					PrintWriter writer = response.getWriter();
//					writer.write(ex.getMessage());
//					writer.flush();
//					
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				ResponseUtils.renderText(response, ex.getMessage());
				return null;
			}
		} else {
			return null;
		}
	}
}
