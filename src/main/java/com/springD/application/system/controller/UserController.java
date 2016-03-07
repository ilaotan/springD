package com.springD.application.system.controller;

import com.springD.application.system.entity.User;
import com.springD.application.system.service.RoleService;
import com.springD.application.system.service.UserPermissionService;
import com.springD.framework.common.Constants;
import com.springD.framework.persistence.Page;
import com.springD.framework.shiro.ShiroUser;
import com.springD.framework.utils.Identities;
import com.springD.framework.utils.ResponseUtils;
import com.springD.framework.utils.StringUtils;
import com.springD.framework.utils.UserUtils;
import com.springD.framework.web.BaseController;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/system")
public class UserController extends BaseController{
	
	@Autowired
	UserPermissionService userPermissionService;
	
	@Autowired
	RoleService roleService;
	

	/**
	 * @创建人：tanliansheng
	 * @创建时间： 2014年11月10日 下午2:09:42
	 * @方法描述： roleMap 可以做个缓存 现在是直接拿的
	 * @修改记录：
	 * @since  1.0.0
	*/
	@RequestMapping(value="user/update",method=RequestMethod.GET)
	public String updateForm(HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session){
		String uid = request.getParameter("uid");
		Map map = new HashMap();
		map.put("uid", uid);
		User newuser = userPermissionService.getOneByMap(map);
		session.setAttribute("newuser", newuser);
		model.addAttribute("user", newuser);
		Map roleMap = roleService.getRoleMap();
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("op","修改");
		model.addAttribute("pn", request.getParameter("pn"));
		return "system/admin/userAdminForm";
	}
	
	/**
	 * @创建人：tanliansheng
	 * @创建时间： 2014年11月10日 下午2:09:23
	 * @方法描述： roleMap 可以做个缓存 现在是直接拿的
	 * @修改记录：
	 * @since  1.0.0
	*/
	@RequestMapping(value="user/update",method=RequestMethod.POST)
	public String updateFormPOST(User user, HttpServletRequest request,
			HttpServletResponse response, Model model, 
			RedirectAttributes redirectAttributes){
		
		// 去除所有权限
		Map roleMap = roleService.getRoleMap();

		//校验出错 邮箱为空
		model.addAttribute("user", user);
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("op", Constants.BUTTON_TEXT_SUBMIT);
		
		String url = "system/role/roleForm";

		
		if (!beanValidator(model, user)){
			model.addAttribute("messageType", Constants.MESSAGE_TYPE_ERROR);
			return url;
		}

		//修改
		String md5pwd = request.getParameter("md5pwd");
		String salt = Identities.randomBase62(8);
		
		Map map = new HashMap();
		map.put("uid", user.getUid());
		map.put("email", user.getEmail());
		map.put("phone", user.getPhone());
		map.put("sex", user.getSex());
		map.put("roleId", user.getRoleId());
		map.put("status", user.getStatus());
		
		if(md5pwd!=null && !md5pwd.trim().equals("")){
			map.put("password", Identities.md5Password(salt, md5pwd));
			map.put("salt", salt);
		}
		
		int num = userPermissionService.updateUserByUid(map);
		
		if (num == 0) {
			model.addAttribute("messageType", Constants.MESSAGE_TYPE_ERROR);
			model.addAttribute("message", "操作失败！");
			url = "system/admin/userAdminForm";
		} else {
			
			if (StringUtils.isNotBlank(user.getUid())) {
				model.addAttribute("messageType", Constants.MESSAGE_TYPE_SUCCESS);
				model.addAttribute("message", "操作成功！");
				url = "system/admin/userAdminForm";
			} else {
				addMessage(redirectAttributes, "操作成功！");
				url = "redirect:/system/user/showList";
			}
		}
		return url;
	}
	/**
	 * 删除用户JS
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="user/delete",method=RequestMethod.GET)
	public String deleteForm(HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session){
		String uid = request.getParameter("uid");
		Map map = new HashMap();
		map.put("uid", uid);
		Map mapResult = new HashMap();
		try {
			int num = userPermissionService.deletByUid(Integer.valueOf(uid));
			
			if(num==0){//失败
				mapResult.put("message", "删除失败");
			}else{
				mapResult.put("message", "删除成功");
			}
		} catch (Exception e) {
			
			mapResult.put("message", "删除失败");
			mapResult.put("error", e.getStackTrace());
		}
		ResponseUtils.renderJson(response, mapResult);
		return null;
	}
	
	///** 显示系统管理员账号
	// * @param request
	// * @param response
	// * @param model
	// * @return
	// */
	//@RequestMapping(value="usersystem/showList",method=RequestMethod.GET)
	//public String showUserSystemList(HttpServletRequest request,HttpServletResponse response,Model model){
	//
	//	String cxUserName = request.getParameter("cxUserName");
	//	String cxEmail = request.getParameter("cxEmail");
	//	String cxSex = request.getParameter("cxSex");
	//	String cxPhone = request.getParameter("cxPhone");
	//	model.addAttribute("cxUserName", cxUserName);
	//	model.addAttribute("cxEmail", cxEmail);
	//	model.addAttribute("cxPhone", cxPhone);
	//	model.addAttribute("cxSex", cxSex);
	//
	//	if(cxUserName == null || cxUserName.trim().equals(""))
	//		cxUserName=null;
	//	if(cxEmail == null || cxEmail.trim().equals(""))
	//		cxEmail=null;
	//	if(cxPhone == null || cxPhone.trim().equals(""))
	//		cxPhone=null;
	//	// 默认分页大小
	//	Page page = getPage(request);
	//
	//	Map map = new HashMap();
	//	//可选
	//	//map.put("creator", "");
	//	map.put("page", page);
	//	map.put("username", cxUserName);
	//	map.put("email", cxEmail);
	//	map.put("phone", cxPhone);
	//	map.put("userType", "1");
	//	if(cxSex!=null && !"0".equals(cxSex)) {
	//		map.put("sex", cxSex);
	//	}
	//
	//	List userList= userPermissionService.getAllForPage(map);
	//	page.setList(userList);
	//	model.addAttribute("page", page);
	//
	//	return "system/admin/userSystemList";
	//}
	
	@RequestMapping(value="usersystem/add",method=RequestMethod.GET)
	public String addUserSystem(HttpServletRequest request,HttpServletResponse response,Model model){
		
		User newuser = new User();
		model.addAttribute("user", newuser);
		Map roleMap = roleService.getRoleMap();
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("op","增加");
		model.addAttribute("pn", request.getParameter("pn"));
		return "system/admin/userSystemForm";
	}
	
	@RequestMapping(value="usersystem/add",method=RequestMethod.POST)
	public String addUserSystemPOST(User newuser,HttpServletRequest request,HttpServletResponse response,Model model
			,RedirectAttributes redirectAttributes){
		String md5pwd = request.getParameter("md5pwd");
		
		// 生成salt
		String salt = Identities.randomBase62(8);
		String newpwd = null;
		if(md5pwd!=null && !md5pwd.equals("")){
			newpwd =  Identities.md5Password(salt, md5pwd);
			newuser.setPassword(newpwd);
			newuser.setSalt(salt);
		}
		
		newuser.setRegip(StringUtils.getRemoteAddr(request));
		newuser.setUsertype("1");
		newuser.setStatus("1");
		int num = userPermissionService.saveUser(newuser);
		if(num>0){
			//更新成功 回到列表
			addMessage(redirectAttributes, "操作成功！");
			return "redirect:/system/usersystem/showList";
		}
		model.addAttribute("user", newuser);
		Map roleMap = roleService.getRoleMap();
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("messageType", Constants.MESSAGE_TYPE_ERROR);
		model.addAttribute("message", "操作失败！");
		model.addAttribute("op","增加");
		model.addAttribute("pn", request.getParameter("pn"));
		return "system/admin/userSystemForm";
	}
	
	/** 只增加管理员账号
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="usersystem/update",method=RequestMethod.GET)
	public String updateUserSystemForm(HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session){
		String uid = request.getParameter("uid");
		Map map = new HashMap();
		map.put("uid", uid);
		User newuser = userPermissionService.getOneByMap(map);
		session.setAttribute("newuser", newuser);
		model.addAttribute("user", newuser);
		Map roleMap = roleService.getRoleMap();
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("op","修改");
		model.addAttribute("pn", request.getParameter("pn"));
		return "system/admin/userSystemForm";
	}
	/** 只增加管理员账号
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="usersystem/update",method=RequestMethod.POST)
	public String updateUserSystemFormPOST(User user, HttpServletRequest request,
			HttpServletResponse response, Model model, 
			RedirectAttributes redirectAttributes){
		String md5pwd = request.getParameter("md5pwd");
		
		Map roleMap = roleService.getRoleMap();
		model.addAttribute("user", user);
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("op", Constants.BUTTON_TEXT_SUBMIT);
		
		String url = "";
		
		if (!beanValidator(model, user)){
			model.addAttribute("messageType", Constants.MESSAGE_TYPE_ERROR);
			return url;
		}
		String salt = Identities.randomBase62(8);
		if(md5pwd==null || md5pwd.equals("")){
			user.setPassword(null);
		}else{
			user.setPassword(Identities.md5Password(salt, md5pwd));
			user.setSalt(salt);
		}
		//修改
		
		Map map = new HashMap();
		map.put("uid", user.getUid());
		map.put("username", user.getUsername());
		map.put("email", user.getEmail());
		map.put("phone", user.getPhone());
		map.put("sex", user.getSex());
		map.put("roleId", user.getRoleId());
		map.put("password", user.getPassword());
		map.put("salt", user.getSalt());
		int num = userPermissionService.updateUserByUid(map);
		
		if (num == 0) {
			model.addAttribute("messageType", Constants.MESSAGE_TYPE_ERROR);
			model.addAttribute("message", "操作失败！");
			url = "system/admin/userSystemForm";
		} else {
			
			if (StringUtils.isNotBlank(user.getUid())) {
				model.addAttribute("messageType", Constants.MESSAGE_TYPE_SUCCESS);
				model.addAttribute("message", "操作成功！");
				url = "system/admin/userSystemForm";
			} else {
				addMessage(redirectAttributes, "操作成功！");
				url = "redirect:/system/usersystem/showList";
			}
		}
		return url;
	}
	
	
	
}

