/**
 * @Project base-api
 * @Package com.cds.base.api.service
 * @Class BaseManageService.java
 * @Date Nov 2, 2020 4:58:38 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.api.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.cds.base.common.result.ResponseResult;

/**
 * @Description 基础管理服务
 * @Notes 未填写备注
 * @author liming
 * @Date Nov 2, 2020 4:58:38 PM
 */
public interface BaseManageService<VO> {
    // uri前缀
    final static String BASE_PREFIX = "/manage";

    /**
     * @description 保存
     * @return ResponseResult<Boolean>
     */
    ResponseResult<VO> save(@RequestBody VO value);

    /**
     * @description 批量保存
     * @param ts
     */
    ResponseResult<Integer> saveAll(@RequestBody List<VO> valueList);

    /**
     * @description 修改
     * @return ResponseResult<Boolean>
     */
    ResponseResult<VO> modify(@RequestBody VO value);
}
