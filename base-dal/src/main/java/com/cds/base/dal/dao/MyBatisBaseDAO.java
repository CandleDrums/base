/**
 * @Project base-dal
 * @Package com.cds.base.dal.dao
 * @Class MyBatisBaseDAO.java
 * @Date Aug 6, 2020 5:35:08 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.dal.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * @Description MyBatis公共基础
 * @Notes 如果使用MyBatis Plus可以继承此接口
 * @author liming
 * @Date Aug 6, 2020 5:35:08 PM
 */
public interface MyBatisBaseDAO<DO, PK extends Serializable, Example> {
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
