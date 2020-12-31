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
    public ResponseResult<VO> detail(@NotNull VO value) {
        List<VO> resultList = getService().queryAll(value);
        if (CheckUtils.isEmpty(resultList)) {
            return ResponseResult.returnNull(null);
        }
        if (resultList.size() != 1) {
            return ResponseResult.returnFail(null, "存在多条相同记录");
        }
        return ResponseResult.returnSuccess(resultList.get(0));
    }

    @Override
    public ResponseResult<List<VO>> queryAll(@NotNull VO params) {
        List<VO> resultList = null;
        resultList = getService().queryAll(params);
        if (CheckUtils.isEmpty(resultList)) {
            return ResponseResult.returnNull(null);
        }
        return ResponseResult.returnSuccess(resultList);
    }

    @Override
    public ResponseResult<Page<VO>> queryPagingList(@NotNull Page<VO> page) {
        if (page == null) {
            page = new Page<>();
        }
        if (page.getPageNum() <= 0) {
            page.setPageNum(Page.DEFAULT_PAGE_NUM);
        }
        if (page.getPageSize() <= 0) {
            page.setPageSize(Page.DEFAULT_PAGE_SIZE);
        }
        Page<VO> pageResult = getService().queryPagingList(page.getParam(), page.getPageNum(), page.getPageSize());
        return ResponseResult.returnSuccess(pageResult);
    }
}
