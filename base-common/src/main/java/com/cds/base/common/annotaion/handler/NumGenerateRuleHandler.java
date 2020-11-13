/**
 * @Project base-common
 * @Package com.cds.base.common.annotaion.handler
 * @Class NumGenerateRuleHandler.java
 * @Date Sep 4, 2020 6:31:28 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.common.annotaion.handler;

import com.cds.base.common.annotaion.NumGenerateRule;
import com.cds.base.common.rule.NumRule;

/**
 * @Description num生成规则处理
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 4, 2020 6:31:28 PM
 */
public class NumGenerateRuleHandler {

    public static NumRule getNumRule(Class<?> clazz) {
        NumGenerateRule annotation = clazz.getAnnotation(NumGenerateRule.class);
        if (annotation == null) {
            return null;
        }
        String prefixCode = annotation.prefixCode();
        int ruleCode = annotation.ruleCode();
        return new NumRule(prefixCode, ruleCode);
    }
}
