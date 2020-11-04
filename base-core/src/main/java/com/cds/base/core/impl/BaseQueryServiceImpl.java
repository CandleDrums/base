/**
 * @Project base-core
 * @Package com.cds.base.core.impl
 * @Class BaseQueryService.java
 * @Date Nov 3, 2020 10:02:42 AM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.core.impl;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cds.base.api.service.BaseQueryService;
import com.cds.base.biz.service.BaseService;
import com.cds.base.common.page.Page;
import com.cds.base.common.page.PageResult;
import com.cds.base.common.result.ResponseResult;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 基础查询服务实现
 * @Notes 未填写备注
 * @author liming
 * @Date Nov 3, 2020 10:02:42 AM
 */
public abstract class BaseQueryServiceImpl<VO> implements BaseQueryService<VO> {

    protected abstract BaseService<VO> getService();

    @Override
    public ResponseResult<VO> detail(@NotNull Serializable pk) {
        return ResponseResult.returnSuccess(getService().detail(pk));
    }

    @Override
    public ResponseResult<List<VO>> queryAll(VO params) {
        List<VO> resultList = null;
        resultList = getService().queryAll(params);
        if (CheckUtils.isEmpty(resultList)) {
            return ResponseResult.returnNull(null);
        }
        return ResponseResult.returnSuccess(resultList);
    }

    @Override
    public ResponseResult<PageResult<VO>> queryPagingList(@NotNull Page<VO> page) {
        List<VO> resultList = null;
        resultList = getService().queryPagingList(page.getParam(), page.getStartIndex(), page.getPageSize());
        if (CheckUtils.isEmpty(resultList)) {
            return ResponseResult.returnNull(null);
        }
        int resultCount = getService().queryPagingCount(page.getParam());

        PageResult<VO> pageResult = new PageResult<VO>();
        pageResult.setResultList(resultList);
        pageResult.setResultCount(resultCount);
        return ResponseResult.returnSuccess(pageResult);
    }
}
