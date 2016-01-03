package com.springD.application.system.entity;


import java.io.Serializable;

import javax.validation.constraints.Size;


public class Role implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;	//id
	@Size(min=3,max=20,message="角色名称需大于3个小于20个字符")	private String name;
	private String status;
	private String remark;
	@Size(min=3,max=20,message="角色编码需大于3个小于20个字符")
	private String code;
	private String menuIds;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
