/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class BaseDAO.java
 * @Date 2018年2月7日 下午5:37:57
 * @Copyright (c) CandleDrumS.com All Right Reserved.
 */
package com.cds.base.dal.dao;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @Description 基础DAO
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 18, 2019 10:59:38 AM
 * @version 1.0
 * @since JDK 1.8
 */
public interface BaseDAO<T>
    extends BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>, ExampleMapper<T>, InsertListMapper<T>, MySqlMapper<T> {

}
