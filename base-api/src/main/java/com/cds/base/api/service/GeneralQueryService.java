/**
 * @Project base-api
 * @Package com.cds.base.api.service
 * @Class GeneralQueryService.java
 * @Date Oct 31, 2019 6:41:35 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.api.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.common.page.Page;
import com.cds.base.common.page.PageResult;
import com.cds.base.common.result.ResponseResult;

/**
 * @Description 业务查询Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:41:35 PM
 * @version 1.0
 * @since JDK 1.8
 */
public interface GeneralQueryService<VO> {
    // uri前缀
    static String BASE_PREFIX = "/query";

    /**
     * @description 根据业务主键查询
     * @return ResponseResult<T>
     */
    ResponseResult<VO> detail(@RequestParam(value = "num", required = true) String num);

    /**
     * @description 判断是否存在
     * @return ResponseResult<Boolean>
     */
    ResponseResult<Boolean> contains(@RequestBody VO value);

    /**
     * @description 根据实体值查询
     * @return ResponseResult<List<T>>
     */
    ResponseResult<List<VO>> queryAll(@RequestBody VO value);

    /**
     * @description 分页查询
     * @return ResponseResult<List<T>>
     */
    ResponseResult<PageResult<VO>> queryPagingList(@RequestBody Page<VO> page);
}
