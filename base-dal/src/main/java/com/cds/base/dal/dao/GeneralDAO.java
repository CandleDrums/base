/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class GeneralDAO.java
 * @Date Oct 31, 2019 6:08:28 PM
 * @Copyright (c) 2019 YOUWE All Right Reserved.
 */
package com.cds.base.dal.dao;

import java.util.List;

/**
 * @Description 业务DAO
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:08:28 PM
 * @version 1.0
 * @since JDK 1.8
 */
public interface GeneralDAO<DO> extends BaseDAO<DO> {
    /**
     * @description 更新
     * @return void
     */
    void update(DO value);

    /**
     * @description 删除
     * @return void
     */
    void delete(String num);

    /**
     * @description 根据主键查询
     * @return DO
     */
    DO find(String num);

    /**
     * @description 查询列表
     * @return List<DO>
     */
    List<DO> findList(List<String> numList);
}
