/**
 * @Project base-common
 * @Package com.cds.base.common.type
 * @Class ResultType.java
 * @Date Jan 3, 2020 1:57:54 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.type;

/**
 * @Description 结果类型
 * @Notes 未填写备注
 * @author liming
 * @Date Jan 3, 2020 1:57:54 PM
 * @version 1.0
 * @since JDK 1.8
 */
public enum ResultType {
    SUCCESS("SUCCESS", "成功"), FAIL("FAIL", "失败"), TIMEOUT("TIMEOUT", "超时"), UNKNOWN("UNKNOWN", "未知");

    /**
     * 状态值
     */
    private final String value;
    /**
     * 显示名称
     */
    private String displayName;

    private ResultType(String value, String displayName) {
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
