/**
 * @Project base-common
 * @Package com.cds.base.common.rule
 * @Class NumSplicingRule.java
 * @Date 2019年9月16日 下午6:33:09
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved
 */
package com.cds.base.common.rule;

/**
 * @Description 编码拼接规则
 * @Notes 3=TIMESTAMP+RANDOM; 5=TIMESTAMP+REDIS_INCR; 6=RANDOM+REDIS_INCR; 7=TIMESTAMP+RANDOM+REDIS_INCR
 * @author maoshou
 * @Date 2019年9月16日 下午6:33:09
 * @version 1.0
 * @since JDK 1.8
 */
public enum NumSplicingRule {

    DEFAULT(0, "无"), TIMESTAMP(1, "时间戳"), RANDOM(2, "随机数"), REDIS_INCR(4, "自增长");

    /**
     * 状态值
     */
    private final int intValue;
    /**
     * 显示名称
     */
    private String displayName;

    private NumSplicingRule(int intValue, String displayName) {
        this.intValue = intValue;
        this.displayName = displayName;
    }

    /**
     * @description 根据值获取枚举
     * @return NumSplicingRule
     */
    public static NumSplicingRule getNumSplicingRule(int value) {
        for (NumSplicingRule rule : NumSplicingRule.values()) {
            if (rule.intValue() == value) {
                return rule;
            }
        }
        return null;
    }

    /**
     * intValue
     *
     * @return intValue
     */

    public int intValue() {
        return intValue;
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
