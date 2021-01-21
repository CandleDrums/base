/**
 * @Project base-rest
 * @Package com.cds.base.rest.fallback
 * @Class DefaultFallbackFactory.java
 * @Date 2021年1月21日 上午10:43:05
 * @Copyright (c) 2021 CandleDrums.com All Right Reserved.
 */
package com.cds.base.rest.fallback;

import org.apache.poi.ss.formula.functions.T;

import com.cds.base.util.bean.JsonUtils;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date 2021年1月21日 上午10:43:05
 */
@Slf4j
public class DefaultFallbackFactory implements FallbackFactory<T> {
    final T constant;

    public DefaultFallbackFactory(T constant) {
        this.constant = constant;
    }

    @Override
    public T create(Throwable e) {
        log.error(e.getMessage(), e);
        return constant;
    }

    @Override
    public String toString() {
        return JsonUtils.getJson(constant);
    }
}