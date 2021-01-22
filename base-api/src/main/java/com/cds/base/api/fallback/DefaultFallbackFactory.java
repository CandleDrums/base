/**
 * @Project base-api
 * @Package com.cds.base.api.fallback
 * @Class DefaultFallbackFactory.java
 * @Date 2021年1月21日 上午11:21:52
 * @Copyright (c) 2021 CandleDrums.com All Right Reserved.
 */
package com.cds.base.api.fallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;

import com.cds.base.common.result.ResponseResult;

import cn.hutool.aop.ProxyUtil;
import feign.FeignException;
import feign.Request;
import feign.RetryableException;
import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description 抽象FallbackFactory实现
 * @Notes 未填写备注
 * @author liming
 * @Date 2021年1月21日 上午11:21:52
 */
@Slf4j
@Component
public class DefaultFallbackFactory<T> implements FallbackFactory<T> {
    private static final ThrowableAnalyzer THROWABLE_ANALYZER = new ThrowableAnalyzer();

    /**
     * 获取泛型
     */
    @SuppressWarnings("unchecked")
    protected DefaultFallbackFactory() {
        // 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        // ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
        // Type[] types = pt.getActualTypeArguments(); // 返回表示此类型实际类型参数的 Type 对象的数组
        // type = (Class<T>)types[0];
    }

    @Override
    public T create(Throwable cause) {
        // 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] types = pt.getActualTypeArguments(); // 返回表示此类型实际类型参数的 Type 对象的数组
        Class<T> type = (Class<T>)types[0];
        return createFallbackService(cause, type);
    }

    private T createFallbackService(Throwable ex, Class<?> targetClass) {
        Throwable[] causeChain = THROWABLE_ANALYZER.determineCauseChain(ex);
        RetryableException ase1 =
            (RetryableException)THROWABLE_ANALYZER.getFirstThrowableOfType(RetryableException.class, causeChain);
        log.error("服务出错了", ex);
        if (ase1 != null) {
            return toResponse("服务[{}]接口调用超时！", ase1.request());
        }
        FeignException ase2 =
            (FeignException)THROWABLE_ANALYZER.getFirstThrowableOfType(FeignException.class, causeChain);
        if (ase2 != null) {
            return toResponse("服务[{}]接口调用出错了！", ase2.request());
        }

        // 创建一个JDK代理类
        return ProxyUtil.newProxyInstance((proxy, method, args) -> ResponseResult.returnFail(null, ex.getMessage()),
            targetClass);
    }

    private T toResponse(String respMsg, Request request) {
        Target<?> target = request.requestTemplate().feignTarget();
        Class<?> targetClazz = target.type();
        String serviceName = target.name();
        return ProxyUtil.newProxyInstance((proxy, method, args) -> ResponseResult.returnFail(null, respMsg),
            targetClazz);
    }
}
