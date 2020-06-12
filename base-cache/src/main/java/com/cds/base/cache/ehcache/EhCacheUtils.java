/**
 * @Project base-cache
 * @Package com.cds.base.cache.ehcache
 * @Class EhCacheUtils.java
 * @Date Jun 11, 2020 6:33:20 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.cache.ehcache;

import java.lang.reflect.ParameterizedType;

import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.base.util.bean.BeanUtils;

/**
 * @Description ehCache工具
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 11, 2020 6:33:20 PM
 */
@Component
public class EhCacheUtils<T> {
    @Autowired
    private Cache<String, Object> ehCache;

    public T getCache(String key) {
        Object object = ehCache.get(key);
        if (object == null) {
            return null;
        }
        return BeanUtils.getObject(object, getTClass());
    }

    public void putCache(String key, T t) {
        ehCache.put(key, t);
    }

    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
