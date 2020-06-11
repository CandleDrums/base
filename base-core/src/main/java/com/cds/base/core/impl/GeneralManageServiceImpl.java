/**
 * @Project base-core
 * @Package com.cds.base.core.impl
 * @Class GeneralManageServiceImpl.java
 * @Date Oct 31, 2019 6:39:35 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.core.impl;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.GeneralManageService;
import com.cds.base.biz.service.GeneralService;
import com.cds.base.common.code.ResultCode;
import com.cds.base.common.result.ResponseResult;
import com.cds.base.util.bean.BeanUtils;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 业务管理Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:39:35 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class GeneralManageServiceImpl<VO> implements GeneralManageService<VO> {

    protected abstract GeneralService getService();

    @Override
    public ResponseResult<VO> add(@RequestBody @NotNull VO vo) {
        CheckUtils.validate(vo);
        boolean isExisted = getService().contains(vo);
        if (isExisted) {
            return ResponseResult.returnResult(vo, ResultCode.ERROR.name(), "添加失败，数据已存在");
        }
        Object result = getService().save(vo);
        if (result != null) {
            BeanUtils.copyProperties(result, vo);
            return ResponseResult.returnSuccess(vo);
        }
        return ResponseResult.returnResult(vo, ResultCode.FAIL.name(), "添加失败，状态未知");
    }

    @Override
    public ResponseResult<VO> modify(@RequestBody @NotNull VO vo) {

        Object result = getService().modify(vo);
        if (result != null) {
            BeanUtils.copyProperties(result, vo);
            return ResponseResult.returnSuccess(vo);
        }
        return ResponseResult.returnFail(vo);

    }

    @Override
    public ResponseResult<Boolean> delete(@RequestParam(value = "num", required = true) @NotNull String key) {
        return ResponseResult.returnSuccess(getService().delete(key));
    }

}
