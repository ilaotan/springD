package com.springD.application.system.controller;

import com.springD.application.system.entity.Menu;
import com.springD.application.system.entity.Role;
import com.springD.application.system.service.MenuService;
import com.springD.application.system.service.RoleService;
import com.springD.framework.common.Constants;
import com.springD.framework.exception.SystemException;
import com.springD.framework.utils.ResponseUtils;
import com.springD.framework.utils.StringUtils;
import com.springD.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "system/role")
public class RoleController extends BaseController{

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	/**
	 * 添加表单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="form")
	public String form(Role role, Model model, HttpServletRequest request) throws Exception{
		
		model.addAttribute("op", Constants.BUTTON_TEXT_SUBMIT);
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			role = roleService.get(id);
			model.addAttribute("role",role);
		}
		return "system/role/roleForm";
	}
	/**
	 * 角色新增和编辑
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	public String save(Role role, Model model, HttpServletRequest request, 
			RedirectAttributes redirectAttributes) throws Exception{
		
		model.addAttribute("role", role);
		model.addAttribute("op", Constants.BUTTON_TEXT_SUBMIT);
		
		String url = "system/role/roleForm";
		
		if (!beanValidator(model, role)){
			model.addAttribute("messageType", Constants.MESSAGE_TYPE_ERROR);
			return url;
		}
		
		if (StringUtils.isNotBlank(role.getId())) {
			model.addAttribute("messageType", Constants.MESSAGE_TYPE_SUCCESS);
			model.addAttribute("message", "操作成功！");
			url = "system/role/roleForm";
		} else {
			addMessage(redirectAttributes, "操作成功！");
			url = "redirect:/system/role/list";
		}
		
		// 返回内容
		String message = roleService.save(role);
		// 返回内容不为空且不是id
		if (!"".equals(message) && !message.equals(role.getId())) {
			model.addAttribute("messageType", Constants.MESSAGE_TYPE_ERROR);
			model.addAttribute("message", message);
			return url;
		}
		model.addAttribute("messageType", Constants.MESSAGE_TYPE_SUCCESS);
		model.addAttribute("message", "操作成功！");
		return url;
	}
	
	/**
	 * 删除角色
	 * @Description: 根据角色ID删除角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	public String delete(Role role, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();

		try {
			String id = role.getId();
			int num = roleService.delete(id);
			if(num==0){//失败
				map.put("message", "删除失败");
			}else{
				map.put("message", "删除成功");
			}
		} catch (Exception e) {
			map.put("message", "删除失败");
		}

		ResponseUtils.renderJson(response, map);
		return null;
	}
	
	/**
	 * 角色列表
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(HttpServletRequest request, Model model){
		
		String roleName = request.getParameter("roleName");
		String roleStatus = request.getParameter("roleStatus");
		model.addAttribute("roleName", roleName);
		model.addAttribute("roleStatus", roleStatus);
		
		Map param = new HashMap();
		param.put("roleName", roleName);
		param.put("code", "superadmin");
		if (roleStatus != null) {
			param.put("roleStatus", roleStatus);
		}
		
		List<Role> list = roleService.findAll(param);
		model.addAttribute("list", list);
		return "system/role/roleList";
	}
	
	/**
	 * 权限分配
	 * @return
	 */
	@RequestMapping(value="permissionForm",method=RequestMethod.GET)
	public String permissionForm(Model model, HttpServletRequest request){
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Role role = roleService.get(id);
			model.addAttribute("role",role);
			
			List<Menu> menuList = menuService.findAll();
			model.addAttribute("menuList",menuList);
		}else{
			throw new SystemException("角色参数不能为空！");
		}
		return "system/role/permission";
	}
	
	@RequestMapping(value="permissionForm",method=RequestMethod.POST)
	public String permissionFormPOST(Role role,HttpServletRequest request, HttpServletResponse response){
		int num = roleService.savePermission(role);
		Map<String, Object> result = new HashMap<String, Object>();
		if(num>0){
			result.put("message", "保存成功");
		}else{
			result.put("message", "保存失败");
		}
		ResponseUtils.renderJson(response, result);
		return null;
	}
	
	/**
	 * 添加角色
	 * @Description: 添加新角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Model model){

		model.addAttribute("op", Constants.BUTTON_TEXT_SUBMIT);
		return "system/role/roleForm";
	}
}
