/**
 * @Project base-common
 * @Package com.cds.base.common.rule
 * @Class NumRule.java
 * @Date Jun 8, 2020 3:51:33 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.common.rule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cds.base.common.exception.ValidateException;

import lombok.Data;

/**
 * @Description Num生成规则
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 8, 2020 3:51:33 PM
 */
@Data
public class NumRule {

    public static final Map<String, NumRule> ruleMap = new ConcurrentHashMap<String, NumRule>();
    // 前缀
    private String prefixCode;
    // 编号拼接规则<see>NumSplicingRule</see>
    // 1=TIMESTAMP;
    // 2=RANDOM;
    // 3=TIMESTAMP+RANDOM;
    // 4=REDIS_INCR;
    // 5=TIMESTAMP+REDIS_INCR;
    // 6=RANDOM+REDIS_INCR;
    // 7=TIMESTAMP+RANDOM+REDIS_INCR
    private int numSplicingRule;

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
            throw new ValidateException("注册失败，请确认类型");
        }
        // 去掉DO/VO后缀
        return className.substring(0, className.length() - 2);
    }
}
