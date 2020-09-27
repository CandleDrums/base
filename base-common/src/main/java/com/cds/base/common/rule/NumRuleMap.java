/**
 * @Project base-common
 * @Package com.cds.base.common.rule
 * @Class NumRuleMap.java
 * @Date Jun 8, 2020 4:22:37 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.rule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cds.base.common.exception.ValidationException;

/**
 * @Description Num规则Map
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 8, 2020 4:22:37 PM
 */
@Deprecated
public class NumRuleMap {
    private static final Map<String, NumRule> ruleMap = new ConcurrentHashMap<String, NumRule>();

    /**
     * @description 注册规则
     * @return void
     */
    public static void regRule(Class t, NumRule rule) {
        ruleMap.put(getModelName(t), rule);
    }

    /**
     * @description 获取规则
     * @return NumRule
     */
    public static NumRule getNumRule(Class t) {
        return ruleMap.get(getModelName(t));
    }

    private static String getModelName(Class t) {
        String className = t.getSimpleName();
        if (className.lastIndexOf("DO") <= 0 || className.lastIndexOf("VO") <= 0) {
            throw new ValidationException("注册失败，请确认类型");
        }
        // 去掉DO/VO后缀
        return className.substring(0, className.length() - 2);
    }
}
