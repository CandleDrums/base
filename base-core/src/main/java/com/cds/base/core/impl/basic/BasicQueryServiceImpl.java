/**
 * @Project base-core
 * @Package com.cds.base.core.impl.basic
 * @Class BasicQueryServiceImpl.java
 * @Date Oct 31, 2019 6:51:25 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.core.impl.basic;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.BaseQueryService;
import com.cds.base.biz.service.BasicService;
import com.cds.base.common.result.ResponseResult;
import com.cds.base.core.impl.BaseQueryServiceImpl;

/**
 * @Description 基础查询Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:51:25 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class BasicQueryServiceImpl<VO> extends BaseQueryServiceImpl<VO> implements BaseQueryService<VO> {

    @Override
    protected abstract BasicService<VO> getService();

    @Override
    public ResponseResult<VO> detail(@RequestParam(value = "pk", required = true) Serializable pk) {
        VO data = getService().detail(Integer.valueOf(pk.toString()));
        return ResponseResult.returnSuccess(data);
    }

}
