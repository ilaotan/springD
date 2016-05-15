package com.springD.framework.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.base.delete.DeleteByPrimaryKeyMapper;

/**
 * 继承自己的MyMapper
 *
 * @author liuzh_3nofxnp
 * @since 2015-09-06 21:53
 */
public interface MyMapper<T> extends
        BaseSelectMapper<T>, BaseInsertMapper<T>, BaseUpdateMapper<T>,DeleteByPrimaryKeyMapper<T>,Marker,MySqlMapper<T>  {

}
