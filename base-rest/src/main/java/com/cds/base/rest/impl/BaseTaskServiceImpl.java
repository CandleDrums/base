/**
 * @Project base-core
 * @Package com.cds.base.core.impl
 * @Class BaseTaskService.java
 * @Date Jul 2, 2020 5:51:55 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.rest.impl;

import com.cds.base.api.service.BaseTaskService;
import com.cds.base.common.result.ResponseResult;

/**
 * @Description 基础任务服务实现
 * @Notes 未填写备注
 * @author liming
 * @Date Jul 2, 2020 5:51:55 PM
 */
public abstract class BaseTaskServiceImpl<VO> implements BaseTaskService<VO> {

    @Override
    public abstract ResponseResult<Boolean> submit(VO value);

}
