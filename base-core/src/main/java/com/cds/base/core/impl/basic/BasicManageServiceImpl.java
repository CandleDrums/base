/**
 * @Project base-core
 * @Package com.cds.base.core.impl.basic
 * @Class BasicManageServiceImpl.java
 * @Date Oct 31, 2019 6:52:07 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.core.impl.basic;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.BaseManageService;
import com.cds.base.biz.service.BasicService;
import com.cds.base.common.result.ResponseResult;
import com.cds.base.core.impl.BaseManageServiceImpl;
import com.cds.base.util.bean.BeanUtils;

/**
 * @Description 基本管理Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:52:07 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class BasicManageServiceImpl<VO> extends BaseManageServiceImpl<VO> implements BaseManageService<VO> {

    @Override
    protected abstract BasicService<VO> getService();

    @Override
    public ResponseResult<Boolean> delete(@RequestParam(value = "pk", required = true) @NotNull Serializable pk) {
        return ResponseResult.returnResult(getService().delete(Integer.parseInt(pk.toString())));
    }

    @Override
    public ResponseResult<Integer>
        deleteAll(@RequestParam(value = "pkList", required = true) List<Serializable> pkList) {
        return ResponseResult.returnSuccess(getService().deleteAll(BeanUtils.getObjectList(pkList, Integer.class)));
    }
}
