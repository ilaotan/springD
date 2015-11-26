package com.springD.application.system.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号
	 */
	private String uid;
	/**
	 * 姓名
	 */
	@NotNull(message="用户名长度需大于三个字符小于20个字符")
	@Size(min=3,max=20)
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否有效,是/否/离职
	 */
	private String status;
	/**
	 * 用户所有部门
	 */
	private List<Org> userOrgs;
	/**
	 * 用户所有角色
	 */
	private List<Role> userRoles;
	
	private String roleId;//角色ID
	
	private String code;//角色code
	
	private String salt;
	
	private int kindergartenId =0;
	
	private String regip;
	
	private Date regdate;
	
	private String lastloginip;
	
	private Date lastlogindate;
	
	private String roleName;
	
	private String usertype;
	
	public User() {
		super();
	}
	
	public String getUsertype() {
		return usertype;
	}
	
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	
	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	/**
	 * @创建人：tanliansheng
	 * @方法描述：
	 */
	
	public Date getLastlogindate() {
		return lastlogindate;
	}

	/**
	 * @创建人：tanliansheng
	 * @方法描述：
	 */
	public void setLastlogindate(Date lastlogindate) {
		this.lastlogindate = lastlogindate;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<Org> getUserOrgs() {
		return userOrgs;
	}

	public void setUserOrgs(List<Org> userOrgs) {
		this.userOrgs = userOrgs;
	}

	public List<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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
	

	/**
	 * @创建人：tanliansheng
	 * @方法描述：
	 */
	
	public String getRegip() {
		return regip;
	}

	/**
	 * @创建人：tanliansheng
	 * @方法描述：
	 */
	public void setRegip(String regip) {
		this.regip = regip;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password="
				+ password + ", sex=" + sex + ", phone=" + phone + ", mobile="
				+ mobile + ", email=" + email + ", address=" + address
				+ ", remark=" + remark + ", status=" + status + ", userOrgs="
				+ userOrgs + ", userRoles=" + userRoles + ", roleId=" + roleId
				+ ", code=" + code + ", salt=" + salt + ", kindergartenId="
				+ kindergartenId + "]";
	}
	
	
	
}
