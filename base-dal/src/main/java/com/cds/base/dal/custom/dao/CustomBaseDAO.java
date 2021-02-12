/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class CustomBaseDAO.java
 * @Date Sep 4, 2020 5:03:17 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.dal.custom.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 自定义DAO接口
 * @Notes 适用于无num等实体
 * @author liming
 * @Date Sep 4, 2020 5:03:17 PM
 */
public interface CustomBaseDAO<DO> {
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
     * @description 更新
     * @return void
     */
    Integer modify(DO value);

    /**
     * @description 根据主键查询
     * @return DO
     */
    DO detail(Serializable pk);

    /**
     * @description 指定参数查询（key为类属性名）
     * @return List<DO>
     */
    List<DO> queryAll(DO params);

    /**
     * @description 分页查询
     * @return List<DO>
     */
    List<DO> queryPagingList(DO params, Integer startIndex, Integer pageSize);

    /**
     * @description 总数查询
     * @return int
     */
    int queryPagingCount(DO params);

    /**
     * @description 删除
     * @return void
     */
    Integer delete(Serializable pk);

}
