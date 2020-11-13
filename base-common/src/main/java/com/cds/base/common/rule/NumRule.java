/**
 * @Project base-common
 * @Package com.cds.base.common.rule
 * @Class NumRule.java
 * @Date Jun 8, 2020 3:51:33 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.rule;

import com.cds.base.common.annotaion.NumGenerateRule;

import lombok.Data;

/**
 * @Description num规则
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 8, 2020 3:51:33 PM
 */
@Data
public class NumRule {

    // 前缀
    private String prefix;
    // 编号拼接规则<see>NumSplicingRule</see>
    // 1=TIMESTAMP;
    // 2=RANDOM;
    // 3=TIMESTAMP+RANDOM;
    // 4=REDIS_INCR;
    // 5=TIMESTAMP+REDIS_INCR;
    // 6=RANDOM+REDIS_INCR;
    // 7=TIMESTAMP+RANDOM+REDIS_INCR
    private int rule;

    public NumRule(String prefix, int rule) {
        this.prefix = prefix;
        this.rule = rule;
    }

    public static NumRule getNumRule(Class<?> clazz) {
        NumGenerateRule annotation = clazz.getAnnotation(NumGenerateRule.class);
        if (annotation == null) {
            return null;
        }
        String prefix = annotation.prefixCode();
        int rule = annotation.ruleCode();
        return new NumRule(prefix, rule);
    }
}
