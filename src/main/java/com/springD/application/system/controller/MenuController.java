package com.springD.application.system.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.springD.application.system.entity.Menu;
import com.springD.application.system.service.MenuService;
import com.springD.framework.config.StaticConstants;
import com.springD.framework.utils.ResponseUtils;
import com.springD.framework.utils.StringUtils;
import com.springD.framework.web.BaseController;

/**
 * 菜单管理
 * @author Chenz
 *
 */
@Controller
@RequestMapping(value = "system/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	/**
	 * 菜单列表
	 * @Description: 菜单列表以树形结构展示
	 * @param menu
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="list")
	public String list(Menu menu, Model model) throws Exception{
		menu.setParentId("0");
		List<Menu> list = Lists.newArrayList();
		List<Menu> orgList = menuService.findAll();
		MenuService.sortList(list, orgList, "0");
		model.addAttribute("list", list);
		return "system/menu/menuList";
	}
	
	/**
	 * 校验未通过表单项重新赋值
	 * @Description: 校验未通过表单项重新赋值
	 * @param menu
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="form")
	public String form(Menu menu, Model model, HttpServletRequest request) throws Exception{
		Menu parentMenu = null;
		model.addAttribute("op", StaticConstants.BUTTON_TEXT_SUBMIT);
		String pid = request.getParameter("pid");
		if(StringUtils.isNotBlank(pid)){
			parentMenu = menuService.get(pid);
		}
		
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			menu = menuService.get(id);
			parentMenu = menuService.get(menu.getParentId());
			model.addAttribute("menu",menu);
		}
		if(parentMenu != null){
			model.addAttribute("pMenuName",parentMenu.getName());
			model.addAttribute("pMenuId",parentMenu.getId());
		}else{
			model.addAttribute("pMenuName","无");
			model.addAttribute("pMenuId",0);
		}
		return "system/menu/menuForm";
	}
	
	/**
	 * 保存菜单
	 * @Description: 添加新菜单或编辑更新菜单方法
	 * @param menu   菜单内容(表单项)
	 * @param model  表单实体
	 * @param request 请求对象
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	public String save(Menu menu, Model model, HttpServletRequest request, 
			RedirectAttributes redirectAttributes) throws Exception{
		
		// 父菜单名称
		String parentName = request.getParameter("parentName");
		model.addAttribute("pMenuName", parentName);
		model.addAttribute("pMenuId", menu.getParentId());
		
		menu.setCreateDate(new Date());
		model.addAttribute("op", StaticConstants.BUTTON_TEXT_SUBMIT);
		//数据校验
		if (!beanValidator(model, menu)){
			model.addAttribute("messageType", StaticConstants.MESSAGE_TYPE_ERROR);
			return form(menu, model, request);
		}
		// 排序值默认1
		if (menu.getListorder() == null) {
			menu.setListorder("1");
		}
		// 默认显示菜单项
		if (menu.getIsshow() == null) {
			menu.setIsshow("1");
		}
		
		String returnUrl = "";
		// 修改操作
		if(StringUtils.isNotBlank(menu.getId())){
			model.addAttribute("messageType", StaticConstants.MESSAGE_TYPE_SUCCESS);
			model.addAttribute("message", "操作成功！");
			returnUrl = "system/menu/menuForm";
		// 添加操作
		} else {
			addMessage(redirectAttributes, "操作成功！");
			returnUrl = "redirect:/system/menu/list.do";
		}
		
		//保存到数据库
		String message = menuService.save(menu);
		
		if (message != null && !"".equals(message) && !menu.getId().equals(message)) {
			model.addAttribute("messageType", StaticConstants.MESSAGE_TYPE_ERROR);
			model.addAttribute("message", message);
			return "system/menu/menuForm";
		}

		return returnUrl;
	}
	
	/**
	 * 保存菜单
	 * @Description: 添加新菜单或编辑更新菜单方法
	 * @param menu   菜单内容(表单项)
	 * @param model  表单实体
	 * @param request 请求对象
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	public String delete(Menu menu, Model model, HttpServletRequest request) throws Exception{
		
		//保存到数据库
		menuService.delete(menu.getId());
		return showSuccess(model.asMap(), "/system/menu/list.do", "操作成功！");
	}
	
	/**
	 * 菜单编辑页面上级菜单树
	 * @Description: 菜单编辑页面上级菜单数据以树形结构显示
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="jsonTreeData")
	public String jsonTreeData(HttpServletResponse response) throws Exception{
		List<Menu> list = menuService.findAll4Tree();
		ResponseUtils.renderJson(response, list);
		return null;
	}
	
}
