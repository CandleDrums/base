/**
 * @Project base-api
 * @Package com.cds.base.api.service
 * @Class BaseTaskService.java
 * @Date Jul 2, 2020 5:04:02 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.api.service;

import com.cds.base.common.result.ResponseResult;

/**
 * @Description 任务服务
 * @Notes 未填写备注
 * @author liming
 * @Date Jul 2, 2020 5:04:02 PM
 */
public interface BaseTaskService<VO> {

    // uri前缀
    final static String BASE_PREFIX = "/task";

    /**
     * @description 提交任务
     * @return ResponseResult<Boolean>
     */
    ResponseResult<Boolean> submit(VO value);

}
