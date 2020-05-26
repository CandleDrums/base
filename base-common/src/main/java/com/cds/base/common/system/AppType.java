/**
 * @Project base-common
 * @Package com.cds.base.common.system
 * @Class AppType.java
 * @Date 2019年9月16日 下午6:12:18
 * @Copyright (c) 2019 CandleDrums.com All Right Reserved.
 */
package com.cds.base.common.system;

/**
 * @Description 应用类型
 * @Notes 未填写备注
 * @author maoshou
 * @Date 2019年9月16日 下午6:12:18
 * @version 1.0
 * @since JDK 1.8
 */
public enum AppType {

    ACCOUNT_CENTER("ACCOUNT_CENTER", "账户中心"), ORDER_CENTER("ORDER_CENTER", "订单中心"),
    MERCHANT_CENTER("MERCHANT_CENTER", "商户中心"), FRIENDSHIP_CENTER("FRIENDSHIP_CENTER", "友商中心"),
    PIONEER_CENTER("PIONEER_CENTER", "开拓者中心"), PERIPHERAL_CENTER("PIONEER_CENTER", "外设中心");

    /**
     * 值
     */
    private final String value;
    /**
     * 显示名称
     */
    private String displayName;

    private AppType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * value
     *
     * @return value
     */

    public String getValue() {
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
