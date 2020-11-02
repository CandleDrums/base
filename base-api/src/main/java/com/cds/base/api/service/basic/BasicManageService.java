/**
 * @Project base-api
 * @Package com.cds.base.api.service
 * @Class BasicManageService.java
 * @Date Oct 8, 2019 11:02:56 AM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.api.service.basic;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.BaseManageService;
import com.cds.base.common.result.ResponseResult;

/**
 * @Description 基础管理Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 8, 2019 11:02:56 AM
 * @version 1.0
 * @since JDK 1.8
 */
public interface BasicManageService<VO> extends BaseManageService<VO> {

    /**
     * @description 逻辑删除
     * @return ResponseResult<Boolean>
     */
    ResponseResult<Boolean> delete(@RequestParam(value = "id", required = true) Integer id);

    /**
     * @description 批量删除
     * @return int
     */
    ResponseResult<Integer> deleteAll(@RequestParam(value = "idList", required = true) List<Integer> idList);
}
