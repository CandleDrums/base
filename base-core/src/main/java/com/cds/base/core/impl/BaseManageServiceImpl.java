/**
 * @Project base-core
 * @Package com.cds.base.core.impl
 * @Class BaseManageServiceImpl.java
 * @Date Nov 3, 2020 11:13:57 AM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.core.impl;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cds.base.api.service.BaseManageService;
import com.cds.base.biz.service.BaseService;
import com.cds.base.common.code.ResultCode;
import com.cds.base.common.result.ResponseResult;
import com.cds.base.util.bean.BeanUtils;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 基础管理服务实现
 * @Notes 未填写备注
 * @author liming
 * @Date Nov 3, 2020 11:13:57 AM
 */
public abstract class BaseManageServiceImpl<VO> implements BaseManageService<VO> {

    protected abstract BaseService<VO> getService();

    @Override
    public ResponseResult<VO> save(@NotNull VO vo) {
        CheckUtils.validate(vo);
        Object result = getService().save(vo);
        if (result != null) {
            BeanUtils.copyProperties(result, vo);
            return ResponseResult.returnSuccess(vo);
        }
        return ResponseResult.returnResult(vo, ResultCode.FAIL.name(), "添加失败，状态未知");
    }

    @Override
    public ResponseResult<VO> modify(@NotNull VO vo) {

        Object result = getService().modify(vo);
        if (result != null) {
            BeanUtils.copyProperties(result, vo);
            return ResponseResult.returnSuccess(vo);
        }
        return ResponseResult.returnFail(vo);
    }

    @Override
    public ResponseResult<Integer> saveAll(@NotNull List<VO> valueList) {
        return ResponseResult.returnSuccess(getService().saveAll(valueList));
    }

    @Override
    public ResponseResult<Boolean> delete(@NotNull Serializable pk) {
        return ResponseResult.returnResult(getService().delete(pk));
    }

}
