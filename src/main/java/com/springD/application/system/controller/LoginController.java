package com.springD.application.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.springD.application.system.entity.User;
import com.springD.application.system.service.UserPermissionService;
import com.springD.framework.common.Constants;
import com.springD.framework.shiro.ShiroUser;
import com.springD.framework.utils.Identities;
import com.springD.framework.utils.UserUtils;
import com.springD.framework.web.BaseController;

@Controller
public class LoginController extends BaseController{

	
	@Autowired
	private UserPermissionService userPermissionService;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	/**
	 * 登录页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="kindergarten/login",method=RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception{
		Boolean isCaptchaRequired = UserUtils.isCaptchaRequired(session);
		request.setAttribute("isCaptchaRequired", isCaptchaRequired);
		return "login/login";
	}
	

	/**
	 * 登录验证
	 * 测试账号 admin  123456
	 * @param crtUser
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="kindergarten/login",method=RequestMethod.POST)
	public String loginPost(User crtUser,HttpSession session, HttpServletRequest request, Model model) throws Exception{
		
		return null;
	}
	
	
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
	 * 系统管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/contractor/login",method=RequestMethod.GET)
	public String contractorLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		//如果已经登录，跳转
		Boolean isCaptchaRequired = UserUtils.isCaptchaRequired(session);
		request.setAttribute("isCaptchaRequired", isCaptchaRequired);
		return "login/contractorLogin";
	}
	
	@RequestMapping(value="/contractor/login",method=RequestMethod.POST)
	public String contractorLoginPOST(User crtUser,HttpSession session, HttpServletRequest request, Model model) throws Exception{
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
		return "redirect:/system/admin.do";
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
	 * @Description: 幼儿园管理员新用户激活
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/anon/activate")
	public String activate(HttpServletRequest request,Model model) throws Exception{
		String message = request.getParameter("message");
		String from = request.getParameter("from");
		String showUrl = Constants.KINDER_LOGIN_URL;
		if("c".equals(from)){//工程商点击
			showUrl = Constants.CONTRACTOR_LOGIN_URL;
		}
		model.addAttribute("showUrl",showUrl);
		if(message==null||message.equals("")){
			message=null;
		}
//		if(message!=null)
//			message = new String(message.getBytes("ISO-8859-1"),"UTF-8");
		model.addAttribute("message",message );
		return "login/activateStep1";
	}
	
	/**
	 * @Description: 幼儿园管理员找回密码
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/anon/findPwd")
	public String findPwd(HttpServletRequest request,Model model) throws Exception{
		String message = request.getParameter("message");
		String from = request.getParameter("from");
		String showUrl = Constants.KINDER_LOGIN_URL;
		if("c".equals(from)){//工程商点击
			showUrl = Constants.CONTRACTOR_LOGIN_URL;
		}
		model.addAttribute("showUrl",showUrl);
		if(message==null||message.equals("")){
			message=null;
		}
//		if(message!=null)
//			message = new String(message.getBytes("ISO-8859-1"),"UTF-8");
		model.addAttribute("message",message );
		return "login/findPwdStep1";
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
			if (3 == roleId || 5 == roleId || 6 == roleId) {
				url = "http://system.aiwebaby.com/system/admin.do";
			// 幼儿园
			} else if (4 == roleId) {
				url = "http://manager.aiwebaby.com/kindergarten/admin.do";
			// 工程商
			} else if (7 == roleId) {
				url = "http://project.aiwebaby.com/contractor/admin.do";
			}
		} else {
			url = "http://manager.aiwebaby.com/";
		}
		
		model.addAttribute("url", url);
		return "exception/unauth";
	}
	
	@RequestMapping(value="anon/console")
	public String console() throws Exception{
		return "exception/console";
	}
}
