package com.springD.application.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;


public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	@Size(min=2,max=20,message="菜单名称长度需大于2个字符小于20个字符")
	private String name;
	private String parentId;
	@Size(min=1,max=255,message="不能为空")
	private String url;
	private String remark;
	private String isshow;
	private String parentIds;
	@Digits(fraction = 0, integer = 5, message="菜单排序值需要输入正整数")
	private String listorder;
	private Date createDate;
	
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsshow() {
		return isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getListorder() {
		return listorder;
	}
	public void setListorder(String listorder) {
		this.listorder = listorder;
	}
	public boolean isRootNode() {
        return parentId.equals("0");
    }
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
