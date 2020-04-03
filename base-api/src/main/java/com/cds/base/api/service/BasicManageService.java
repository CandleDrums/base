/**
 * @Project base-api
 * @Package com.cds.base.api.service
 * @Class BasicManageService.java
 * @Date Oct 8, 2019 11:02:56 AM
 * @Copyright (c) 2019 YOUWE All Right Reserved.
 */
package com.cds.base.api.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.common.result.ResponseResult;

/**
 * @Description 基础管理Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 8, 2019 11:02:56 AM
 * @version 1.0
 * @since JDK 1.8
 */
public interface BasicManageService<VO> {
    // uri前缀
    final static String BASE_PREFIX = "/manage";

    /**
     * @description 保存
     * @return ResponseResult<Boolean>
     */
    ResponseResult<VO> add(@RequestBody VO value);

    /**
     * @description 逻辑删除
     * @return ResponseResult<Boolean>
     */
    ResponseResult<Boolean> delete(@RequestParam(value = "id", required = true) Integer id);

}
