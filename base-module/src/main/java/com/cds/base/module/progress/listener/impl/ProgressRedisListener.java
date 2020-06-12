/**
 * @Project base-module
 * @Package com.cds.base.module.progress.listener.impl
 * @Class ProgressRedisListener.java
 * @Date Jun 11, 2020 5:36:12 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.module.progress.listener.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.base.module.progress.model.Progress;
import com.cds.base.redis.client.RedisClient;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 进度监听器Redis实现
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 11, 2020 5:36:12 PM
 */
@Component
public class ProgressRedisListener extends AbstractProgressListener {

    @Autowired
    private RedisClient<Progress> redisClient;

    public ProgressRedisListener() {
        startProgress(null, 1, 100);
    }

    @Override
    public void setProgress(String name, Progress p) {
        if (CheckUtils.isEmpty(name)) {
            redisClient.set(PROGRESS_NAME_PREFIX, p, 60);
        }
        redisClient.set(PROGRESS_NAME_PREFIX + name, p, 60);
    }

    @Override
    public Progress getProgress(String name) {
        if (CheckUtils.isEmpty(name)) {
            return redisClient.get(PROGRESS_NAME_PREFIX);
        }
        return redisClient.get(PROGRESS_NAME_PREFIX + name);

    }
}
