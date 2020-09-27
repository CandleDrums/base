/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service
 * @Class BaseService.java
 * @Date 2019年9月10日 下午2:03:00
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.biz.service;

import java.util.List;

/**
 * @Description 基础Service
 * @Notes 未填写备注
 * @author liming
 * @Date 2019年9月10日 下午2:03:00
 * @version 1.0
 * @since JDK 1.8
 */
public interface BaseService<VO> {

    /**
     * @description 保存
     * @param t(实体值)
     */
    VO save(VO value);

    /**
     * @description 批量保存
     * @param ts
     */
    int saveAll(List<VO> valueList);

    /**
     * @description 判断是否存在
     * @param t(实体值)
     */
    boolean contains(VO value);

    /**
     * @description 根据条件查询
     * @return VO
     */
    VO detail(VO value);

    /**
     * @description 查询全部
     * @return List<T>
     */
    List<VO> queryAll(VO params);

    /**
     * @description 分页查询
     * @return List<T>
     */
    List<VO> queryPagingList(VO params, int startIndex, int pageSize);

    /**
     * @description 查询总数
     * @return int
     */
    int queryPagingCount(VO params);
}
