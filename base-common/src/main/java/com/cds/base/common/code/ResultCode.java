/**
 * @Project base-common
 * @Package com.cds.base.common.enums
 * @Class ResultCode.java
 * @Date 2018年2月8日 下午3:57:08
 * @Copyright (c) CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.code;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author ming.li
 * @Date 2018年2月8日 下午3:57:08
 * @version 1.0
 * @since JDK 1.8
 */
public enum ResultCode {

    // 默认类型
    SUCCESS(0, "SUCCESS", "成功"),
    // 类型1
    FAIL(1, "FAIL", "失败"),
    // 类型2
    ERROR(2, "ERROR", "错误"),
    // 无数据
    NULL(3, "NULL", "无数据"),
    // 类型3
    UNKNOWN(-1, "UNKNOWN", "未知");

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
