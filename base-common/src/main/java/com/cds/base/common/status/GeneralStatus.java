/**
 * @Project base-common
 * @Package com.cds.base.common.status
 * @Class GeneralStatus.java
 * @Date 2019年9月10日 下午5:22:09
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.status;

/**
 * @Description 通用状态
 * @Notes 未填写备注
 * @author maoshou
 * @Date 2019年9月10日 下午5:22:09
 * @version 1.0
 * @since JDK 1.8
 */
public enum GeneralStatus {
    NEW("NEW", "新建"), NORMAL("NORMAL", "正常"), FROZEN("FROZEN", "冻结"), STOP("STOP", "停止");
    /**
     * 状态值
     */
    private final String value;
    /**
     * 显示名称
     */
    private String displayName;

    private GeneralStatus(String value, String displayName) {
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
