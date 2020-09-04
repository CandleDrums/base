/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class BaseDAO.java
 * @Date Sep 4, 2020 5:03:17 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.dal.custom.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 4, 2020 5:03:17 PM
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
