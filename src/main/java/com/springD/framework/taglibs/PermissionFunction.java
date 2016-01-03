package com.springD.framework.taglibs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.springD.framework.utils.StringUtils;

/**
 * 权限相关
 * @author Chenz
 *
 */
public class PermissionFunction {

	public static boolean isIn(String iterableStr, Object element) {
    	//Iterable iterable
    	Iterable iterable  = string2List(iterableStr);
        if(iterable == null) {
            return false;
        }
        return CollectionUtils.contains(iterable.iterator(), element);
    }
	
	/**
     * 将string的内容转为list
     * @param roleIds
     * @return
     */
    public static List<String> string2List(String strIds){
    	List<String> idsList = new ArrayList<String>(); 
    	if(strIds!=null){
    		try {
            	String[] roleIdStrs = strIds.split(",");
                for(String roleIdStr : roleIdStrs) {
                    if(StringUtils.isEmpty(roleIdStr)) {
                        continue;
                    }
                    idsList.add(roleIdStr);
                }
    		} catch (Exception e) {
    			System.out.println("出异常了strIds==>"+strIds);
    		}
    	}
    	return idsList;
    }
}
