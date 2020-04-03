/**
 * @Project base-module
 * @Package com.cds.base.module.printer.paper
 * @Class PaperCategory.java
 * @Date Jan 8, 2020 3:12:23 PM
 * @Copyright (c) 2020 YOUWE All Right Reserved.
 */
package com.cds.base.module.printer.paper;

/**
 * @Description 纸张类别
 * @Notes 未填写备注
 * @author liming
 * @Date Jan 8, 2020 3:12:23 PM
 * @version 1.0
 * @since JDK 1.8
 */
public enum PaperCategory {

    THERMAL_PAPER("1", "热敏纸"), LABEL_PAPER("2", "标签纸"), HOLES_PAPER("3", "带孔纸");

    /**
     * 状态值
     */
    private final String value;
    /**
     * 显示名称
     */
    private String displayName;

    private PaperCategory(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getValue() {
        return value;
    }
}
