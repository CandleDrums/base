/**
 * @Project base-cache
 * @Package com.cds.base.cache.ehcache
 * @Class EhCacheUtils.java
 * @Date Jun 11, 2020 6:33:20 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.cache.ehcache;

import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description ehCache工具
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 11, 2020 6:33:20 PM
 */
@Component
public class EhCacheUtils<T> {
    @Autowired
    private Cache<String, String> ehCache;

    /**
     * @description 获取
     * @return T
     */
    public T getCache(String key, Class type) {
        String value = ehCache.get(key);
        if (CheckUtils.isEmpty(value)) {
            return null;
        }
        return (T)JSON.parseObject(value, type);
    }

    /**
     * @description 存入
     * @return void
     */
    public void putCache(String key, T t) {
        ehCache.put(key, JSON.toJSONString(t));
    }

}
