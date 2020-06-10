/**
 * @Project base-common
 * @Package com.cds.base.common.type
 * @Class GeneralType.java
 * @Date 2019年9月10日 下午5:21:53
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved
 */
package com.cds.base.common.type;

/**
 * @Description 通用类型
 * @Notes 未填写备注
 * @author maoshou
 * @Date 2019年9月10日 下午5:21:53
 * @version 1.0
 * @since JDK 1.8
 */
public enum GeneralType {
    GROUP("GROUP", "集团"), MERCHANT("MERCHANT", "商户"), SHOP("SHOP", "店铺"), USER("USER", "用户"), ORDER("ORDER", "订单");
    /**
     * 状态值
     */
    private final String value;
    /**
     * 显示名称
     */
    private String displayName;

    private GeneralType(String value, String displayName) {
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
