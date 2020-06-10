/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class BasicDAO.java
 * @Date Oct 31, 2019 6:08:16 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved
 */
package com.cds.base.dal.dao;

import java.util.List;

/**
 * @Description 基本DAO
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:08:16 PM
 * @version 1.0
 * @since JDK 1.8
 */
public interface BasicDAO<DO> extends BaseDAO<DO> {

    /**
     * @description 删除
     * @return void
     */
    Integer delete(Integer id);

    /**
     * @description 详情
     * @return DO
     */
    DO detail(Integer id);

    /**
     * @description 查询列表
     * @return List<DO>
     */
    List<DO> detailList(List<Integer> idList);
}
