/**
 * @Project base-core
 * @Package com.cds.base.core.impl.general
 * @Class GeneralManageServiceImpl.java
 * @Date Oct 31, 2019 6:39:35 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.core.impl.general;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.BaseManageService;
import com.cds.base.biz.service.GeneralService;
import com.cds.base.common.result.ResponseResult;
import com.cds.base.core.impl.BaseManageServiceImpl;
import com.cds.base.util.bean.BeanUtils;

/**
 * @Description 业务管理Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:39:35 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class GeneralManageServiceImpl<VO> extends BaseManageServiceImpl<VO> implements BaseManageService<VO> {

    @Override
    protected abstract GeneralService<VO> getService();

    @Override
    public ResponseResult<Boolean> delete(@RequestParam(value = "pk", required = true) @NotNull Serializable pk) {
        return ResponseResult.returnResult(getService().delete(pk.toString()));
    }

    @Override
    public ResponseResult<Integer>
        deleteAll(@RequestParam(value = "pkList", required = true) List<Serializable> pkList) {
        return ResponseResult.returnSuccess(getService().deleteAll(BeanUtils.getObjectList(pkList, String.class)));
    }

}
