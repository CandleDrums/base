/**
 * @Project base-api
 * @Package com.cds.base.api.config
 * @Class FeignConfig.java
 * @Date 2021年1月24日 下午2:24:00
 * @Copyright (c) 2021 CandleDrums.com All Right Reserved.
 */
package com.cds.base.api.config;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;

/**
 * @Description Feign配置
 * @Notes 未填写备注
 * @author liming
 * @Date 2021年1月24日 下午2:24:00
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

    @Value(value = "spring.security.user.name")
    private String userName;
    @Value(value = "spring.security.user.password")
    private String password;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            return;
        }
        requestTemplate.header(HttpHeaders.AUTHORIZATION, authorization);
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(userName, password);
    }

    @Bean
    public Retryer getFeignRetryer() {
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1L), 5);
    }
}
