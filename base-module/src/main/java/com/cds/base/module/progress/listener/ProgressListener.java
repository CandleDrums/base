/**
 * @Project base-module
 * @Package com.cds.base.module.progress.listener
 * @Class ProgressListener.java
 * @Date Jun 8, 2020 4:36:16 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.module.progress.listener;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.cds.base.module.progress.model.Progress;

/**
 * @Description 进度监听器
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 8, 2020 4:36:16 PM
 */
@Component
public class ProgressListener {
    private HttpSession session;
    private static final String PROGRESS_DATA = "PROGRESS_DATA";

    public void startProgress(HttpSession session, int step, int total) {
        this.session = session;
        Progress p = new Progress();
        p.setStep(step);
        p.setTotal(total);
        session.setAttribute(PROGRESS_DATA, p);
    }

    /**
     * @description 更新
     * @return void
     */
    public void update(int current) {

        Progress p = (Progress)session.getAttribute(PROGRESS_DATA);
        if (p == null) {
            p = new Progress();
        }
        p.setCurrent(current);
        session.setAttribute(PROGRESS_DATA, p);
    }

    /**
     * @description 完成
     * @return void
     */
    public void finish() {
        Progress p = (Progress)session.getAttribute(PROGRESS_DATA);
        this.update(p.getTotal());
    }

    /**
     * @description 倍数步增，新进度=原进度+步增*倍数
     * @return void
     */
    public void stepTimes(Integer times) {
        Progress p = (Progress)session.getAttribute(PROGRESS_DATA);
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
        Progress p = (Progress)session.getAttribute(PROGRESS_DATA);
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
     * @description 获取进度
     * @return Progress
     */
    public Progress getProgress() {
        return (Progress)session.getAttribute(PROGRESS_DATA);
    }

}
