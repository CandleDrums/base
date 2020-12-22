/**
 * @Project base-api
 * @Package com.cds.base.api.service
 * @Class BaseQueryService.java
 * @Date Nov 2, 2020 4:54:37 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.api.service;

import java.io.Serializable;
import java.util.List;

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
     * @description 根据业务主键查询
     * @return ResponseResult<T>
     */
    ResponseResult<VO> detail(Serializable pk);

    /**
     * @description 根据实体值查询唯一记录
     * @return ResponseResult<VO>
     */
    ResponseResult<VO> detail(VO value);

    /**
     * @description 根据实体值查询
     * @return ResponseResult<List<T>>
     */
    ResponseResult<List<VO>> queryAll(VO value);

    /**
     * @description 分页查询
     * @return ResponseResult<List<T>>
     */
    ResponseResult<PageResult<VO>> queryPagingList(Page<VO> page);

}
