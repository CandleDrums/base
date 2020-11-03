/**
 * @Project base-core
 * @Package com.cds.base.core.impl.general
 * @Class GeneralManageServiceImpl.java
 * @Date Oct 31, 2019 6:39:35 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.core.impl.general;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.general.GeneralManageService;
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

    protected abstract GeneralService<VO> getService();

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
    public ResponseResult<VO> modify(@RequestBody @NotNull VO vo) {

        Object result = getService().modify(vo);
        if (result != null) {
            BeanUtils.copyProperties(result, vo);
            return ResponseResult.returnSuccess(vo);
        }
        return ResponseResult.returnFail(vo);
    }

    @Override
    public ResponseResult<Integer> saveAll(@RequestBody @NotNull List<VO> valueList) {
        return ResponseResult.returnSuccess(getService().saveAll(valueList));
    }

    @Override
    public ResponseResult<Integer> deleteAll(@RequestParam(value = "numList", required = true) List<String> numList) {
        return ResponseResult.returnSuccess(getService().deleteAll(numList));
    }

    @Override
    public ResponseResult<Boolean> delete(@RequestParam(value = "num", required = true) @NotNull String key) {
        return ResponseResult.returnResult(getService().delete(key));
    }

}
