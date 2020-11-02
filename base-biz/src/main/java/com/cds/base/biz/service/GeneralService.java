/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service
 * @Class GeneralService.java
 * @Date Oct 31, 2019 6:13:16 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.biz.service;

import java.util.List;

/**
 * @Description 业务Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:13:16 PM
 * @version 1.0
 * @since JDK 1.8
 */
public interface GeneralService<VO> extends BaseService<VO> {

    /**
     * @description 根据key查询
     * @param num(数据库主键)
     */
    VO detail(String num);

    /**
     * @description 删除
     * @return boolean
     */
    boolean delete(String num);

    /**
     * @description 批量删除
     * @return int
     */
    int deleteAll(List<String> numList);
}
