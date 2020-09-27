/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class BaseDAO.java
 * @Date 2018年2月7日 下午5:37:57
 * @Copyright (c) CandleDrumS.com All Right Reserved.
 */
package com.cds.base.dal.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * @Description 基础DAO
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 18, 2019 10:59:38 AM
 * @version 1.0
 * @since JDK 1.8
 */
public interface BaseDAO<DO, PK extends Serializable, Example> {
    long countByExample(Example example);

    int deleteByExample(Example example);

    int deleteByPrimaryKey(PK id);

    int insert(DO record);

    int insertSelective(DO record);

    List<DO> selectByExample(Example example);

    DO selectByPrimaryKey(PK id);

    int updateByExampleSelective(@Param("record") DO record, @Param("example") Example example);

    int updateByExample(@Param("record") DO record, @Param("example") Example example);

    int updateByPrimaryKeySelective(DO record);

    int updateByPrimaryKey(DO record);

    List<DO> queryPagingList(DO record, RowBounds bounds);

    int queryPagingCount(DO record);

}
