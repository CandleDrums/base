/**
 * @Project base-core
 * @Package com.cds.base.core.impl
 * @Class BasicManageServiceImpl.java
 * @Date Oct 31, 2019 6:52:07 PM
 * @Copyright (c) 2019 CandleDrums.com All Right Reserved.
 */
package com.cds.base.core.impl;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.BasicManageService;
import com.cds.base.biz.service.BasicService;
import com.cds.base.common.code.ResultCode;
import com.cds.base.common.result.ResponseResult;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 基本管理Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:52:07 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class BasicManageServiceImpl<VO> implements BasicManageService<VO> {

    protected abstract BasicService getService();

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
    public ResponseResult<Boolean> delete(@RequestParam(value = "id", required = true) @NotNull Integer id) {
        return ResponseResult.returnSuccess(getService().delete(id));
    }
}
