/**
 * @Project base-module
 * @Package com.cds.base.module.progress.listener.impl
 * @Class ProgressLocalCacheListener.java
 * @Date Jun 11, 2020 5:58:23 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.module.progress.listener.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.base.cache.ehcache.EhCacheUtils;
import com.cds.base.module.progress.model.Progress;

/**
 * @Description 进度监听器EhCache实现
 * @Notes 缺点是无法多进程共享
 * @author liming
 * @Date Jun 11, 2020 5:58:23 PM
 */
@Component("progressLocalCacheListener")
public class ProgressLocalCacheListener extends AbstractProgressListener {
    @Autowired
    private EhCacheUtils<Progress> ehCacheUtils;

    @Override
    public void setProgress(String name, Progress p) {
        ehCacheUtils.putCache(name, p);
    }

    @Override
    public Progress getProgress(String name) {
        return ehCacheUtils.getCache(name, Progress.class);
    }

}
