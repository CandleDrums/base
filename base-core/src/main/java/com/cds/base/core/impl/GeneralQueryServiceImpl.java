/**
 * @Project base-core
 * @Package com.cds.base.core.impl
 * @Class GeneralQueryServiceImpl.java
 * @Date Oct 31, 2019 6:51:06 PM
 * @Copyright (c) 2019 CandleDrums.com All Right Reserved.
 */
package com.cds.base.core.impl;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cds.base.api.service.GeneralQueryService;
import com.cds.base.biz.service.GeneralService;
import com.cds.base.common.page.Page;
import com.cds.base.common.page.PageResult;
import com.cds.base.common.result.ResponseResult;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 业务查询Service
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:51:06 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class GeneralQueryServiceImpl<VO> implements GeneralQueryService<VO> {

    protected abstract GeneralService getService();

    @Override
    public ResponseResult<VO> detail(@RequestParam(value = "num", required = true) @NotNull String num) {
        VO data = (VO)getService().detail(num);
        return ResponseResult.returnSuccess(data);
    }

    @Override
    public ResponseResult<Boolean> contains(@RequestBody @NotNull VO value) {

        boolean isExisted = getService().contains(value);
        return ResponseResult.returnSuccess(isExisted);
    }

    @Override
    public ResponseResult<List<VO>> queryAll(@RequestBody @NotNull VO params) {
        List resultList = null;
        resultList = getService().queryAll(params);
        if (CheckUtils.isEmpty(resultList)) {
            return ResponseResult.returnNull(null);
        }
        return ResponseResult.returnSuccess(resultList);
    }

    @Override
    public ResponseResult<PageResult<VO>> queryPagingList(@RequestBody @NotNull Page<VO> page) {
        List<VO> resultList = getService().queryPagingList(page.getParam(), page.getStartIndex(), page.getPageSize());
        int resultCount = getService().queryPagingCount(page.getParam());

        PageResult<VO> pageResult = new PageResult<VO>();
        pageResult.setResultList(resultList);
        pageResult.setResultCount(resultCount);
        return ResponseResult.returnSuccess(pageResult);
    }
}
