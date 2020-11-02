/**
 * @Project base-api
 * @Package com.cds.base.api.service
 * @Class BaseQueryService.java
 * @Date Nov 2, 2020 4:54:37 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.api.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.cds.base.common.page.Page;
import com.cds.base.common.page.PageResult;
import com.cds.base.common.result.ResponseResult;

/**
 * @Description 基础查询服务
 * @Notes 未填写备注
 * @author liming
 * @Date Nov 2, 2020 4:54:37 PM
 */
public interface BaseQueryService<VO> {

    // uri前缀
    final static String BASE_PREFIX = "/query";

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
