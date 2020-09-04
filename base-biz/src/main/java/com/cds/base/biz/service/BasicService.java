/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service
 * @Class BasicService.java
 * @Date Oct 31, 2019 6:13:00 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.biz.service;

import java.util.List;

/**
 * @Description 基本Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:13:00 PM
 * @version 1.0
 * @since JDK 1.8
 */
public interface BasicService<VO> extends BaseService<VO> {

    /**
     * @description 根据key查询
     * @param id(数据库主键)
     */
    VO detail(Integer id);

    /**
     * @description 根据key查询
     * @param id(数据库主键)
     */
    List<VO> findList(List<Integer> idList);

    /**
     * @description 删除
     * @return boolean
     */
    boolean delete(Integer id);

    /**
     * @description 批量删除
     * @return int
     */
    int deleteAll(List<Integer> idList);
}
