package com.springD.application.system.service;

import com.springD.application.system.dao.MenuMapper;
import com.springD.application.system.entity.Menu;
import com.springD.framework.datasource.DatabaseContextHolder;
import com.springD.framework.exception.SystemException;
import com.springD.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class MenuService {

	@Autowired
	private MenuMapper menuMapper;

	public void setMenuMapper(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}

	public Menu get(String id){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return menuMapper.findById(id);
	}

	public List<Menu> findAll(){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return menuMapper.findAll();
	}

	public List<Menu> findAll4Tree(){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return menuMapper.findAll4Tree();
	}

	/**
	 * 切换数据源外层方法
	 * @param menu
	 * @return
	 */
	public String save(Menu menu){
		return saveTran(menu);
	}

	/**
	 * 保存菜单数据
	 * @Description: 菜单修改和添加时，保存菜单数据
	 * @param menu : 菜单数据
	 * @return
	 */
	@Transactional
	public String saveTran(Menu menu){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		String oldParentIds = menu.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		//查找新的父级
		Menu parentMenu = menuMapper.findById(menu.getParentId());
		if(parentMenu != null){
			menu.setParentIds(parentMenu.getParentIds()+menu.getParentId()+",");
		}else{
			menu.setParentIds("0,");
		}
		try {
			if(StringUtils.isNotBlank(menu.getId())){

				List<Menu> lstSame = menuMapper.findBeforeInsert(menu);
				if (lstSame != null && lstSame.size() > 1) {
					return "菜单名称与已有数据重复，请修改后重新提交。";
				}

				menuMapper.update(menu);
			}else{

				List<Menu> lstSame = menuMapper.findBeforeInsert(menu);
				if (lstSame != null && lstSame.size() > 0) {
					return "菜单名称与已有数据重复，请修改后重新提交。";
				}
				menuMapper.save(menu);
			}
			// 更新子节点 parentIds
			List<Menu> list = menuMapper.findByParentIdsLike(","+menu.getId()+",");
			for (Menu o : list) {
				if (oldParentIds != null && !"".equals(oldParentIds) && oldParentIds.equals(menu.getParentIds())) {
					continue;
				}
				o.setParentIds(o.getParentIds().replace(oldParentIds, menu.getParentIds()));
				menuMapper.update(o);
			}
			return menu.getId();
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}

	}

	/**
	 * 切换数据源外层
	 * @param id
	 * @return
	 */
	public int delete(String id){
		return deleteTran(id);
	}

	/**
	 * 删除菜单
	 * @Description: 按菜单ID删除菜单
	 * @param id     菜单ID
	 * @return       删除数
	 */
	@Transactional
	public int deleteTran(String id){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		try {

			menuMapper.delete(id);
			return menuMapper.deleteByPid("," + id + ",");

		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}

	/**
	 * 按父子类排序,用到了递归 有待优化
	 * @param list
	 * @param sourcelist
	 * @param parentId
	 */
	public static void sortList(List<Menu> list, List<Menu> sourcelist, String parentId){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		for (int i=0; i<sourcelist.size(); i++){
			Menu e = sourcelist.get(i);
			if (e.getParentId()!=null && e.getParentId().equals(parentId)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					Menu child = sourcelist.get(j);
					if (child.getParentId()!=null && child.getParentId().equals(e.getId())){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}


}