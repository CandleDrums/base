/**
 * @Project base-module
 * @Package com.cds.base.module.printer
 * @Class PrinterStatus.java
 * @Date Jan 9, 2020 5:53:03 PM
 * @Copyright (c) 2020 YOUWE All Right Reserved.
 */
package com.cds.base.module.printer.status;

/**
 * @Description 打印机状态
 * @Notes 未填写备注
 * @author liming
 * @Date Jan 9, 2020 5:53:03 PM
 * @version 1.0
 * @since JDK 1.8
 */
public enum PrinterState {
    CODE_0(0, "打印机不存在"), CODE_1(1, "在线"), CODE_2(2, "缺纸"), CODE_3(3, "故障或开盖"), CODE_4(4, "离线"), CODE_5(5, "选择纸张错误"),
    CODE_6(6, "不属于此应用id"), CODE_7(7, "未绑定"), CODE_8(8, "已绑定到其他商户"), CODE_99(99, "其他异常");

    /**
     * 状态值
     */
    private final int value;
    /**
     * 显示名称
     */
    private String displayName;

    PrinterState(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * value
     *
     * @return value
     */

    public int getValue() {
        return value;
    }

    /**
     * displayName
     *
     * @return displayName
     */

    public String getDisplayName() {
        return displayName;
    }
}
