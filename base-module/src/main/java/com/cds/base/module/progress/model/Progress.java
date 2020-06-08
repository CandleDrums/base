/**
 * @Project base-module
 * @Package com.cds.base.module.progress.model
 * @Class Progress.java
 * @Date Jun 8, 2020 4:30:25 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.module.progress.model;

import lombok.Data;

/**
 * @Description 进度模型
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 8, 2020 4:30:25 PM
 */
@Data
public class Progress {

    // 当前总进度百分比
    private Double percent = 0.0;
    // 当前值
    private int current = 0;
    // 递增值
    private int step = 1;
    // 总值
    private int total = 100;

    public Progress() {}

    public Progress(int current, int step, int total) {
        this.current = current;
        this.step = step;
        this.total = total;
    }

    public Progress(int current, int total) {
        this.current = current;
        this.total = total;
    }

    /**
     * @description 获取进度
     * @return Double
     */
    public Double getPercent() {
        if (current > 0) {
            return Double.valueOf(current / total);
        }
        return percent;
    }
}