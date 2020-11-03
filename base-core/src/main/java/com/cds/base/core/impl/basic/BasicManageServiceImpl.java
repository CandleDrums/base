/**
 * @Project base-core
 * @Package com.cds.base.core.impl.basic
 * @Class BasicManageServiceImpl.java
 * @Date Oct 31, 2019 6:52:07 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.core.impl.basic;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.basic.BasicManageService;
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

    protected abstract BasicService<VO> getService();

    @Override
    public ResponseResult<VO> save(@RequestBody @NotNull VO vo) {
        CheckUtils.validate(vo);
        Object result = getService().save(vo);
        if (result != null) {
            BeanUtils.copyProperties(result, vo);
            return ResponseResult.returnSuccess(vo);
        }
        return ResponseResult.returnResult(vo, ResultCode.FAIL.name(), "添加失败，状态未知");
    }

    @Override
    public ResponseResult<Integer> saveAll(@RequestBody @NotNull List<VO> valueList) {
        return ResponseResult.returnSuccess(getService().saveAll(valueList));
    }

    @Override
    public ResponseResult<Boolean> delete(@RequestParam(value = "id", required = true) @NotNull Integer id) {
        return ResponseResult.returnResult(getService().delete(id));
    }

    @Override
    public ResponseResult<Integer> deleteAll(@RequestParam(value = "idList", required = true) List<Integer> idList) {
        return ResponseResult.returnSuccess(getService().deleteAll(idList));
    }
}
