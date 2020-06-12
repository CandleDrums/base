/**
 * @Project base-module
 * @Package com.cds.base.module.progress.listener
 * @Class ProgressListener.java
 * @Date Jun 11, 2020 5:25:57 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.module.progress.listener;

import com.cds.base.module.progress.model.Progress;

/**
 * @Description 进度监听
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 11, 2020 5:25:57 PM
 */
public interface ProgressListener {

    /**
     * @description 开始
     * @return void
     */
    void startProgress(String name, int step, int total);

    /**
     * @description 更新
     * @return void
     */
    void update(String name, int current);

    /**
     * @description 完成
     * @return void
     */
    void finish(String name);

    /**
     * @description 倍数步增，新进度=原进度+步增*倍数
     * @return void
     */
    void stepTimes(String name, Integer times);

    /**
     * @description 单倍步增
     * @return void
     */
    void step(String name);

    /**
     * @description 获取进度,已初始化后
     * @return Progress
     */
    Progress getProgress(String name);

}