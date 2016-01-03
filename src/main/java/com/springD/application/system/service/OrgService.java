package com.springD.application.system.service;

import com.springD.application.system.dao.OrgMapper;
import com.springD.application.system.entity.Org;
import com.springD.framework.exception.SystemException;
import com.springD.framework.utils.StringUtils;
import com.springD.framework.datasource.DatabaseContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgService {

	@Autowired
	private OrgMapper orgMapper;

	//
	public Org get(String id) {
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return orgMapper.findById(id);
	}

	/**
	 * 插入组织数据
	 * @param org
	 * @return
	 */
	public String save(Org org){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		String oldParentIds = org.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		//查找新的父级
		Org parentOrg = this.get(org.getParentId());
		org.setParentIds(parentOrg.getParentIds()+org.getParentId()+",");
		try {
			if(StringUtils.isNotBlank(org.getId())){
				orgMapper.update(org);
			}else{
				orgMapper.save(org);
			}
			// 更新子节点 parentIds
			List<Org> list = orgMapper.findByParentIdsLike(","+org.getId()+",");
			for (Org o : list) {
				o.setParentIds(o.getParentIds().replace(oldParentIds, o.getParentIds()));
				orgMapper.update(o);
			}
			return org.getId();
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}

	}
	public List<Org> findAll(){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return orgMapper.findAll();
	}
	/**
	 * 按父子类排序,用到了递归 有待优化
	 * @param list
	 * @param sourcelist
	 * @param parentId
	 */
	public static void sortList(List<Org> list, List<Org> sourcelist, String parentId){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		for (int i=0; i<sourcelist.size(); i++){
			Org e = sourcelist.get(i);
			if (e.getParentId()!=null && e.getParentId().equals(parentId)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					Org child = sourcelist.get(j);
					if (child.getParentId()!=null && child.getParentId().equals(e.getId())){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}

	/**
	 *
	 * @param id
	 */
	public void delete(String id){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		try {
			orgMapper.delete(id);
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}

}
