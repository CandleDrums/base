/**
 * @Project base-api
 * @Package com.cds.base.api.service
 * @Class GeneralManageService.java
 * @Date Oct 31, 2019 6:41:22 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.api.service.general;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.BaseManageService;
import com.cds.base.common.result.ResponseResult;

/**
 * @Description 业务管理Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:41:22 PM
 * @version 1.0
 * @since JDK 1.8
 */
public interface GeneralManageService<VO> extends BaseManageService<VO> {

    /**
     * @description 逻辑删除
     * @return ResponseResult<Boolean>
     */
    ResponseResult<Boolean> delete(@RequestParam(value = "num", required = true) String num);

    /**
     * @description 批量删除
     * @return int
     */
    ResponseResult<Integer> deleteAll(@RequestParam(value = "numList", required = true) List<String> numList);
}
