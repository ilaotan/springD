package com.springD.application.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springD.framework.datasource.DatabaseContextHolder;

import com.springD.application.system.dao.RoleMapper;
import com.springD.application.system.entity.Role;
import com.springD.framework.exception.SystemException;
import com.springD.framework.utils.StringUtils;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public Role get(String id){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return roleMapper.findById(id);
	}

	/**
	 * 保存角色数据
	 * @param role
	 * @return
	 */
	@Transactional
	public String save(Role role){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		try {

			if(StringUtils.isNotBlank(role.getId())){

				// 先校验是否已经存在同名或同编码数据
				List<Role> list = roleMapper.findBeforeInsert(role);
				if (list != null && list.size() > 1) {
					return "角色名称或角色CODE与已有数据重复，请修改后重新提交。";
				}

				roleMapper.update(role);
			}else{

				// 先校验是否已经存在同名或同编码数据
				List<Role> list = roleMapper.findBeforeInsert(role);
				if (list != null && list.size() > 0) {
					return "角色名称或角色CODE与已有数据重复，请修改后重新提交。";
				}

				roleMapper.save(role);
			}

			return role.getId();
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}

	/**
	 * 删除角色
	 * @Description: 根据角色ID删除角色
	 * @param id     角色ID
	 * @return
	 */
	public int delete(String id) {
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		int deleteCnt = 0;
		try {
			// 获取删除条数
			deleteCnt = roleMapper.delete(id);
			if (deleteCnt == 0) {
				Role role = roleMapper.findById(id);
				if (role == null) {

					throw new SystemException("该权限已删除。");
				} else {

					throw new SystemException("请确保没有用户使用该权限后，再次进行删除操作。");
				}
			}
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
		return deleteCnt;
	}

	public List<Role> findAll(Map param){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return roleMapper.findAll(param);
	}

	/**
	 * 保存权限
	 * @param role
	 * @return
	 */
	public String savePermission(Role role){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		try {
			roleMapper.savePermission(role);
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
		return role.getId();
	}

	/**
	 * @创建人：tanliansheng
	 * @创建时间： 2014年11月10日 下午2:03:47
	 * @方法描述：key --value形式的map key是roleid 方便jsp页面的select框
	 * @修改记录：
	 * @since  1.0.0
	 */
	public Map getRoleMap(){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		Map param = new HashMap();
		List<Role> roleList = findAll(param);
		Map map = new HashMap();
		for(Role role:roleList){
			map.put(role.getId(), role.getName());
		}
		return map;
	}
}
