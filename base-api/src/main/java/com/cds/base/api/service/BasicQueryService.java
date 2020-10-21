/**
 * @Project base-api
 * @Package com.cds.base.api.service
 * @Class BasicQueryService.java
 * @Date Oct 8, 2019 11:03:32 AM
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
 * @Description 基础查询Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 8, 2019 11:03:32 AM
 * @version 1.0
 * @since JDK 1.8
 */
public interface BasicQueryService<VO> {
    // uri前缀
    final static String BASE_PREFIX = "/query";

    /**
     * @description 根据业务主键查询
     * @return ResponseResult<T>
     */
    ResponseResult<VO> detail(@RequestParam(value = "id", required = true) Integer id);

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
