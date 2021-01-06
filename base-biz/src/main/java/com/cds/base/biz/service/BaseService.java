/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service
 * @Class BaseService.java
 * @Date 2019年9月10日 下午2:03:00
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.biz.service;

import java.io.Serializable;
import java.util.List;

import com.cds.base.common.page.Page;

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
     * @return VO
     */
    VO save(VO value);

    /**
     * @description 批量保存
     * @return int 成功数
     */
    int saveAll(List<VO> valueList);

    /**
     * @description 修改
     * @return VO
     */
    VO modify(VO value);

    /**
     * @description 根据主键查询详情
     * @return VO
     */
    VO detail(Serializable pk);

    /**
     * @description 根据参数查询详情
     * @return VO
     */
    VO detail(VO params);

    /**
     * @description 删除
     * @return boolean
     */
    boolean delete(Serializable pk);

    /**
     * 
     * @description 批量删除
     * @return int 成功数
     */
    int deleteAll(List<Serializable> pkList);

    /**
     * @description 根据参数查询
     * @return List<VO>
     */
    List<VO> queryAll(VO params);

    /**
     * @description 根据参数分页查询
     * @return List<VO>
     */
    Page<VO> queryPaging(VO params, Integer pageNum, Integer pageSize);

    /**
     * @description 查询总数
     * @return int
     */
    int count(VO params);
}
