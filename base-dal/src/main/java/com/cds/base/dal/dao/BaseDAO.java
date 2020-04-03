/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class BaseDAO.java
 * @Date 2018年2月7日 下午5:37:57
 * @Copyright (c) YOUWE All Right Reserved.
 */
package com.cds.base.dal.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * @Description 基础DAO
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 18, 2019 10:59:38 AM
 * @version 1.0
 * @since JDK 1.8
 */
public interface BaseDAO<DO> {

    /**
     * @description 保存
     * @return void
     */
    void save(DO value);

    /**
     * @description 批量保存
     * @return int
     */
    int saveAll(List<DO> valueList);

    /**
     * @description 是否存在
     * @return boolean
     */
    boolean contains(DO value);

    /**
     * @description 指定参数查询（key为类属性名）
     * @return List<DO>
     */
    List<DO> queryAll(DO params);

    /**
     * @description 分页查询
     * @return List<DO>
     */
    List<DO> queryPagingList(DO params, RowBounds bounds);

    /**
     * @description 总数查询
     * @return int
     */
    int queryPagingCount(DO params);

}
