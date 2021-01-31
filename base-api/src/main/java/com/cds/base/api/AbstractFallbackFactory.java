/**
 * @Project base-api
 * @Package com.cds.base.api
 * @Class BaseFallbackFactory.java
 * @Date 2021年1月31日 下午5:10:35
 * @Copyright (c) 2021 CandleDrums.com All Right Reserved.
 */
package com.cds.base.api;

import com.cds.base.common.result.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date 2021年1月31日 下午5:10:35
 */
@Slf4j
public abstract class AbstractFallbackFactory {

    public ResponseResult returnFail() {
        return ResponseResult.returnFail(null, "服务调用失败");
    }

    public ResponseResult returnFail(Object data) {
        return ResponseResult.returnFail(data, "服务调用失败");
    }
}
