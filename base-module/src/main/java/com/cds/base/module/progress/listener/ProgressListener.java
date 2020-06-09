/**
 * @Project base-module
 * @Package com.cds.base.module.progress.listener
 * @Class ProgressListener.java
 * @Date Jun 8, 2020 4:36:16 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.module.progress.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.base.module.progress.model.Progress;
import com.cds.base.redis.client.RedisClient;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 进度监听器
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 8, 2020 4:36:16 PM
 */
@Component
public class ProgressListener {
    @Autowired
    private RedisClient<Progress> redisClient;
    private static final String PROGRESS_DATA = "MODULE:PROGRESS_DATA";
    private String name = PROGRESS_DATA;

    public void startProgress(int step, int total) {
        this.startProgress(null, step, total);
    }

    public void startProgress(String specialName, int step, int total) {
        if (specialName != null) {
            this.name = PROGRESS_DATA + specialName;
        }
        Progress p = new Progress();
        p.setStep(step);
        p.setTotal(total);
        redisClient.set(name, p, 60);
    }

    /**
     * @description 更新
     * @return void
     */
    public void update(int current) {
        Progress p = redisClient.get(name);
        if (p == null) {
            p = new Progress();
        }
        p.setCurrent(current);
        redisClient.set(name, p, 60);
    }

    /**
     * @description 完成
     * @return void
     */
    public void finish() {
        Progress p = redisClient.get(name);
        this.update(p.getTotal());
    }

    /**
     * @description 倍数步增，新进度=原进度+步增*倍数
     * @return void
     */
    public void stepTimes(Integer times) {
        Progress p = redisClient.get(name);
        if (p == null) {
            p = new Progress();
        }
        if (times > 0) {
            int current = p.getCurrent() + p.getStep() * times;
            if (current > p.getTotal()) {
                current = p.getTotal();
            }
            this.update(current);
        }
    }

    /**
     * @description 单倍步增
     * @return void
     */
    public void step() {
        Progress p = redisClient.get(name);
        if (p == null) {
            p = new Progress();
        }

        int current = p.getCurrent() + p.getStep();
        if (current > p.getTotal()) {
            current = p.getTotal();
        }
        this.update(current);
    }

    /**
     * @description 获取进度,已初始化后
     * @return Progress
     */
    public Progress getProgress(String specialName) {
        if (CheckUtils.isEmpty(specialName)) {
            return redisClient.get(name);
        }
        return redisClient.get(PROGRESS_DATA + specialName);

    }

}
