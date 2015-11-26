package com.springD.framework.shiro;


import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.springD.application.system.entity.User;

public class ShiroUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 工号
	 */
	private String workno;
	/**
	 * 账号
	 */
	private String account;

	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 0.女1.男
	 */
	private String sex;
	
	private String roleId;
	
	private String code;
	
	private int kindergartenId;
	
	private String phone;

	public ShiroUser() {

	}

	public ShiroUser(User user) {
		this.id = user.getUid();
		this.name = user.getUsername();
		this.email = user.getEmail();
		this.sex=user.getSex();
		this.roleId=user.getRoleId();
		this.code=user.getCode();
		this.kindergartenId = user.getKindergartenId();
		this.phone = user.getPhone();
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.account;
	}

	/**
	 * 重载equals,只计算account;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getAccount())
			.toHashCode();
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

	public String getWorkno() {
		return workno;
	}

	public void setWorkno(String workno) {
		this.workno = workno;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @创建人：tanliansheng
	 * @方法描述：
	 */
	
	public int getKindergartenId() {
		return kindergartenId;
	}

	/**
	 * @创建人：tanliansheng
	 * @方法描述：
	 */
	public void setKindergartenId(int kindergartenId) {
		this.kindergartenId = kindergartenId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}