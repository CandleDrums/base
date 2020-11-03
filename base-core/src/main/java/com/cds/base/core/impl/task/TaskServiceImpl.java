/**
 * @Project base-core
 * @Package com.cds.base.core.impl.task
 * @Class TaskServiceImpl.java
 * @Date Jul 2, 2020 5:51:55 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.core.impl.task;

import com.cds.base.api.service.TaskService;
import com.cds.base.common.result.ResponseResult;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Jul 2, 2020 5:51:55 PM
 */
public abstract class TaskServiceImpl<VO> implements TaskService<VO> {

    @Override
    public abstract ResponseResult<Boolean> submit(VO value);

}
