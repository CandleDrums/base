/**
 * @Project base-module
 * @Package com.cds.base.module.progress.listener.impl
 * @Class AbstractProgressListener.java
 * @Date Jun 8, 2020 4:36:16 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.module.progress.listener.impl;

import com.cds.base.module.progress.listener.ProgressListener;
import com.cds.base.module.progress.model.Progress;

/**
 * @Description 进度监听器
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 8, 2020 4:36:16 PM
 */
public abstract class AbstractProgressListener implements ProgressListener {
    public static final String PROGRESS_NAME_PREFIX = "MODULE:PROGRESS_DATA|";

    public abstract void setProgress(String name, Progress p);

    @Override
    public abstract Progress getProgress(String name);

    @Override
    public void startProgress(String name, int step, int total) {
        Progress p = new Progress();
        p.setStep(step);
        p.setTotal(total);
        this.setProgress(name, p);
    }

    @Override
    public void update(String name, String message, int current) {
        Progress p = this.getProgress(name);
        if (p == null) {
            p = new Progress();
        }
        p.setCurrent(current);
        p.setMessage(message);
        this.setProgress(name, p);
    }

    @Override
    public void finish(String name, String message) {
        Progress p = this.getProgress(name);
        this.update(name, message, p.getTotal());
    }

    @Override
    public void stepTimes(String name, String message, Integer times) {
        Progress p = this.getProgress(name);
        if (p == null) {
            p = new Progress();
        }
        if (times > 0) {
            int current = p.getCurrent() + p.getStep() * times;
            if (current > p.getTotal()) {
                current = p.getTotal();
            }
            this.update(name, message, current);
        }
    }

    @Override
    public void step(String name, String message) {
        Progress p = this.getProgress(name);
        if (p == null) {
            p = new Progress();
        }

        int current = p.getCurrent() + p.getStep();
        if (current > p.getTotal()) {
            current = p.getTotal();
        }
        this.update(name, message, current);
    }

}
