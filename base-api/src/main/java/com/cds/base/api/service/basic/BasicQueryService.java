/**
 * @Project base-api
 * @Package com.cds.base.api.service.basic
 * @Class BasicQueryService.java
 * @Date Oct 8, 2019 11:03:32 AM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.api.service.basic;

import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.BaseQueryService;
import com.cds.base.common.result.ResponseResult;

/**
 * @Description 基本查询服务
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 8, 2019 11:03:32 AM
 * @version 1.0
 * @since JDK 1.8
 */
public interface BasicQueryService<VO> extends BaseQueryService<VO> {

    /**
     * @description 根据业务主键查询
     * @return ResponseResult<T>
     */
    ResponseResult<VO> detail(@RequestParam(value = "id", required = true) Integer id);

}
