/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class GeneralDAO.java
 * @Date Sep 4, 2020 5:08:59 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.dal.custom.dao;

import java.util.List;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 4, 2020 5:08:59 PM
 */
public interface GeneralDAO<DO> extends BaseDAO<DO> {
    /**
     * @description 更新
     * @return void
     */
    Integer modify(DO value);

    /**
     * @description 删除
     * @return void
     */
    Integer delete(String num);

    /**
     * @description 根据主键查询
     * @return DO
     */
    DO detail(String num);

    /**
     * @description 查询列表
     * @return List<DO>
     */
    List<DO> detailList(List<String> numList);

}
