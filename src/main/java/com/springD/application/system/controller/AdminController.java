package com.springD.application.system.controller;

import com.springD.application.system.entity.User;
import com.springD.application.system.service.UserPermissionService;
import com.springD.framework.shiro.ShiroUser;
import com.springD.framework.utils.Identities;
import com.springD.framework.utils.UserUtils;
import com.springD.framework.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController extends BaseController{

	
	@Autowired
	private UserPermissionService userPermissionService;


	/**
	 * 系统管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/system/admin",method=RequestMethod.GET)
	public String systemLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		//如果已经登录，跳转
		return "system/admin/systemAdmin";
	}
	@RequestMapping(value="/system/welcome",method=RequestMethod.GET)
	public String systemWelcome(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		//如果已经登录，跳转
		return "system/admin/welcome";
	}


	
}
