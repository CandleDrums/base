/**
 * @Project base-module
 * @Package com.cds.base.module.progress.listener.impl
 * @Class ProgressLocalListener.java
 * @Date Jun 16, 2020 10:09:25 AM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.module.progress.listener.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.cds.base.module.progress.model.Progress;

/**
 * @Description 进度监听器本地内存实现
 * @Notes 缺点是需要手动清理
 * @author liming
 * @Date Jun 16, 2020 10:09:25 AM
 */
@Component("progressLocalListener")
public class ProgressLocalListener extends AbstractProgressListener {

    // 放置Progress的Map
    private static final ConcurrentHashMap<String, Progress> PROGRESS_MAP = new ConcurrentHashMap<String, Progress>();

    @Override
    public void setProgress(String name, Progress p) {
        PROGRESS_MAP.put(name, p);
    }

    @Override
    public Progress getProgress(String name) {
        return PROGRESS_MAP.get(name);
    }

}
