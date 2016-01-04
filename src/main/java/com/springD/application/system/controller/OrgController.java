package com.springD.application.system.controller;

import com.google.common.collect.Lists;
import com.springD.application.system.entity.Org;
import com.springD.application.system.service.OrgService;
import com.springD.framework.utils.StringUtils;
import com.springD.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "org")
public class OrgController extends BaseController{
	
	@Autowired
	private OrgService orgService;
	
	@RequestMapping(value="list")
	public String list(Org org, Model model) throws Exception{
		org.setParentId("0");
		List<Org> list = Lists.newArrayList();
		List<Org> orgList = orgService.findAll();
		OrgService.sortList(list, orgList, "0");
		model.addAttribute("list", list);
		return "system/org/orgList";
	}

	/**
	 * 组织机构表单
	 * @param org
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="form")
	public String form(Org org, Model model, HttpServletRequest request) throws Exception{
		Org parentOrg = null;
		
		String pid = request.getParameter("pid");
		if(StringUtils.isNotBlank(pid)){
			parentOrg = orgService.get(pid);
		}
		
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			org = orgService.get(id);
			parentOrg = orgService.get(org.getParentId());
			model.addAttribute("org",org);
		}
		if(parentOrg != null){
			model.addAttribute("pOrgName",parentOrg.getName());
			model.addAttribute("pOrgId",parentOrg.getId());
		}else{
			model.addAttribute("pOrgName","无");
			model.addAttribute("pOrgId",0);
		}
		return "system/org/orgForm";
	}
	/**
	 * 组织机构入库
	 * @param org
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	public String save(Org org, Model model, HttpServletRequest request) throws Exception{
		org.setCreateDate(new Date());
		//数据校验
		if (!beanValidator(model, org)){
			return form(org, model, request);
		}
		//保存到数据库
		orgService.save(org);
		return "";
	}
	
	/**
	 * 单个删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	public String delete(String id) throws Exception{
		if(StringUtils.isNotBlank(id)){
			orgService.delete(id);
		}else{
			
		}
		return null;
	}
}
