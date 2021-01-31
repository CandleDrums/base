/**
 * @Project base-api
 * @Package com.cds.base.api.config
 * @Class FeignConfig.java
 * @Date 2021年1月24日 下午2:24:00
 * @Copyright (c) 2021 CandleDrums.com All Right Reserved.
 */
package com.cds.base.api.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Retryer;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date 2021年1月24日 下午2:24:00
 */
@Configuration
public class FeignConfig {
    @Bean
    public Retryer getFeignRetryer() {
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1L), 5);
    }
}
