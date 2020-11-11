/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class CDSBasicDAO.java
 * @Date Sep 4, 2020 4:58:26 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.dal.custom.dao;

import java.util.List;

/**
 * @Description 基本DAO
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 4, 2020 4:58:26 PM
 */
public interface BasicDAO<DO> extends CustomBaseDAO<DO> {
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
