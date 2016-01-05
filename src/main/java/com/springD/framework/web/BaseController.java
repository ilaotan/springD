package com.springD.framework.web;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.springD.framework.common.BeanValidators;
import com.springD.framework.persistence.Page;
import com.springD.framework.utils.DateUtils;

/**   
 * @Description: 控制器基类
 * @author Chenz
 * @date Nov 13, 2014 1:12:12 PM 
 * @version V1.0   
*/
public abstract class BaseController {

	protected static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;
	@Autowired
	private ImageCaptchaService imageCaptchaService;

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 添加Model消息
	 * @param messages 消息
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
     * @param messages 消息
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("flashmsg", sb.toString());
	}
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 * 3. 处理int double等类型的数据绑定
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
		//Integer类型转换
		binder.registerCustomEditor(Integer.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					if (StringUtils.isNotBlank(value)) {
						setValue(Integer.valueOf(value));
					} else {
						setValue(null);
					}
				} catch (Exception e) {
					setValue(null);
					logger.error(e.getMessage(), e);
				}
			}
		});
		binder.registerCustomEditor(Long.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					if (StringUtils.isNotBlank(value)) {
						setValue(Long.valueOf(value));
					} else {
						setValue(null);
					}
				} catch (Exception e) {
					setValue(null);
					logger.error(e.getMessage(), e);
				}
			}
		});
		binder.registerCustomEditor(Double.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					if (StringUtils.isNotBlank(value)) {
						setValue(Double.valueOf(value));
					} else {
						setValue(null);
					}
				} catch (Exception e) {
					setValue(null);
					logger.error(e.getMessage(), e);
				}
			}
		});

		binder.registerCustomEditor(BigDecimal.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					if (StringUtils.isNotBlank(value)) {
						setValue(new BigDecimal(value));
					} else {
						setValue(null);
					}
				} catch (Exception e) {
					setValue(null);
					logger.error(e.getMessage(), e);
				}
			}
		});
	}
	
	/**
	 * 验证码校验
	 * @param model
	 * @return 1：正确， 0：错误
	 * @throws Exception
	 */
	protected boolean verifyCaptcha(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		boolean checkResult = false;
		String captcha = request.getParameter("captcha");

		try {
			// 校验验证码
			if (captcha != null &&
					imageCaptchaService.validateResponseForID(request.getSession().getId(), captcha)) {
				checkResult = true;
				
			}
		} catch (Exception e) {
			checkResult = false;
		}
		if (!checkResult) {
			model.addAttribute("message", "验证码错误。");
		}
		return checkResult;
	}
	
	/**
	 * @Description: 分页操作
	 * @param request
	 * @return
	 */
	protected Page getPage(HttpServletRequest request){
		//分页操作
		// 默认分页大小
		int pageNo = 1;
		int pageSize = 10;
		String pageNostr = request.getParameter("pageNo");
		String pageSizestr = request.getParameter("pageSize");
		if(pageNostr!=null && !"".equals(pageNostr) && !"null".equals(pageNostr))
			pageNo = Integer.parseInt(pageNostr);
		if(pageSizestr!=null && !"".equals(pageSizestr))
			pageSize = Integer.parseInt(pageSizestr);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}
	
}
