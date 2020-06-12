/**
 * @Project base-cache
 * @Package com.cds.base.cache.config
 * @Class EhCacheConfig.java
 * @Date Jun 12, 2020 3:30:52 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.cache.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description ehCache配置
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 12, 2020 3:30:52 PM
 */
@Configuration
public class EhCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("defaultCache",
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100)).build())
            .build(true);
    }

    @Bean
    public Cache<String, String> ehCache(CacheManager cacheManager) {
        return cacheManager.getCache("defaultCache", String.class, String.class);
    }
}