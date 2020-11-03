/**
 * @Project base-core
 * @Package com.cds.base.core.impl
 * @Class GeneralQueryServiceImpl.java
 * @Date Oct 31, 2019 6:51:06 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.core.impl.general;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.BaseQueryService;
import com.cds.base.biz.service.GeneralService;
import com.cds.base.common.result.ResponseResult;
import com.cds.base.core.impl.BaseQueryServiceImpl;

/**
 * @Description 业务查询Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:51:06 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class GeneralQueryServiceImpl<VO> extends BaseQueryServiceImpl<VO> implements BaseQueryService<VO> {

    @Override
    protected abstract GeneralService<VO> getService();

    @Override
    public ResponseResult<VO> detail(@RequestParam(value = "pk", required = true) @NotNull Serializable pk) {
        return ResponseResult.returnSuccess(getService().detail(pk.toString()));
    }

}
