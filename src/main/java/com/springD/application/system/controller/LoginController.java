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
public class LoginController extends BaseController{

	
	@Autowired
	private UserPermissionService userPermissionService;


	/**
	 * 系统管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/system/login",method=RequestMethod.GET)
	public String systemLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		//如果已经登录，跳转
		Boolean isCaptchaRequired = UserUtils.isCaptchaRequired(session);
		request.setAttribute("isCaptchaRequired", isCaptchaRequired);
		return "login/systemLogin";
	}
	
	@RequestMapping(value="/system/login",method=RequestMethod.POST)
	public String sysLoginPost(User crtUser,HttpSession session, HttpServletRequest request, Model model) throws Exception{
		return null;
	}

	/**
	 */
	@RequestMapping(value="/system/admin/changePwd",method=RequestMethod.GET)
	public String changePwd(HttpSession session,HttpServletRequest request, HttpServletResponse response,Model model){

		return "system/admin/changepwd";
	}
	
	/**
	*/
	@RequestMapping(value="/system/admin/changePwd",method=RequestMethod.POST)
	public String changePwdPost(HttpSession session ,HttpServletRequest request, HttpServletResponse response,Model model,
			RedirectAttributes redirectAttributes){
		
		String newInputPassword = request.getParameter("hpassword");
		String oldInputPassword = request.getParameter("hopassword");
		
		// 用户信息
		ShiroUser user =UserUtils.getShiroUser();
		
		String uid = user.getId();
		Map param = new HashMap();
		param.put("uid", uid);
		User userTmp = userPermissionService.getOneByMap(param);
		
		// 数据库保存的旧密码
		String oldPassword = userTmp.getPassword();
		String oldSalt = userTmp.getSalt();
		
		if (oldInputPassword == null || !oldPassword.equals(Identities.md5Password(oldSalt, oldInputPassword))) {
			model.addAttribute("message", "修改失败，旧密码错误");
			return "system/changepwd";
		}
		// 生成新密码并更新
		String salt = Identities.randomBase62(8);
		String password = Identities.md5Password(salt, newInputPassword);
		Map map = new HashMap();
		map.put("uid", uid);
		map.put("password", password);
		map.put("salt", salt);
		userPermissionService.updateUserByUid(map);
		// 添加提示信息
		addMessage(redirectAttributes, "修改成功！");
		return "redirect:/system/admin";
	}
	
	
	/**
	 * 退出登陆
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) throws Exception{
		Subject subject = SecurityUtils.getSubject();
        if (subject != null) {           
            subject.logout();
        }
        //销毁session
        session.invalidate();
        return "login/login";
	}
	

	/**
	 * @Description: 无权限提示页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login/unauth")
	public String unauth(HttpServletRequest request ,Model model) throws Exception{
		
		String url = "";
		
		// 取用户权限
		ShiroUser user =UserUtils.getShiroUser();
		if (user != null && user.getRoleId() != null) {
			Integer roleId = Integer.parseInt(user.getRoleId());
			// 客服
			url="http://www.baidu.com";
		} else {
			url="http://www.baidu.com";
		}
		
		model.addAttribute("url", url);
		return "exception/unauth";
	}
	
}
