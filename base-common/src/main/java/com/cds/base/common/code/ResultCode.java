/**
 * @Project base-common
 * @Package com.cds.base.common.enums
 * @Class ResultCode.java
 * @Date 2018年2月8日 下午3:57:08
 * @Copyright (c) CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.code;

/**
 * @Description 返回码
 * @Notes 处理失败，出现问题 <--小于 (0) 大于--> 处理成功，但结果不成功
 * @author ming.li
 * @Date 2018年2月8日 下午3:57:08
 * @version 1.0
 * @since JDK 1.8
 */
public enum ResultCode {
    // 未知
    UNKNOWN(-2, "UNKNOWN", "错误"),
    // 错误
    ERROR(-1, "ERROR", "错误"),
    // 成功，默认
    SUCCESS(0, "SUCCESS", "成功"),
    // 失败
    FAIL(1, "FAIL", "失败"),
    // 空值
    NULL(2, "NULL", "空值");

    /**
     * 状态值
     */
    private final int intValue;
    // code
    private final String code;
    /**
     * 显示名称
     */
    private String displayName;

    private ResultCode(int intValue, String code, String displayName) {
        this.intValue = intValue;
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * @description 是否成功
     * @return
     * @returnType boolean
     * @author ming.li
     */
    public boolean isSuccess() {
        return this == SUCCESS;
    }

    /**
     * displayName
     *
     * @return displayName
     */

    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName
     *            the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * intValue
     *
     * @return intValue
     */

    public int getIntValue() {
        return intValue;
    }

    public String getCode() {
        return code;
    }

}
