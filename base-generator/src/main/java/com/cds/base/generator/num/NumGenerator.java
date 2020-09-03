/**
 * @Project base-util
 * @Package com.cds.base.util.generator.num
 * @Class NumGenerator.java
 * @Date 2019年9月16日 下午5:32:31
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.generator.num;

import org.apache.commons.lang3.RandomStringUtils;

import com.cds.base.common.rule.NumRule;
import com.cds.base.common.rule.NumSplicingRule;
import com.cds.base.util.misc.DateUtils;

/**
 * @Description Num生成器
 * @Notes 未填写备注
 * @author maoshou
 * @Date 2019年9月16日 下午5:32:31
 * @version 1.0
 * @since JDK 1.8
 */
public class NumGenerator {

    /**
     * @description 获取编号
     * @return String
     */
    public static String nextNum(NumRule rule) {
        StringBuffer sb = new StringBuffer();
        sb.append(rule.getPrefixCode());
        return generateCode(rule.getNumSplicingRule(), 1, sb);
    }

    /**
     * @description 生成code
     * @return String
     */
    private static String generateCode(int splicingRule, int count, StringBuffer sb) {
        if (count > splicingRule) {
            return sb.toString();
        }
        if (sb == null && count <= 0) {
            sb = new StringBuffer();
            count = 1;
        }
        NumSplicingRule rule = NumSplicingRule.getNumSplicingRule(count);
        if (rule != null) {
            String code = getCodeFromRule(rule);
            sb.append(code);
            return generateCode(splicingRule, count * 2, sb);
        }
        return sb.toString();
    }

    /**
     * @description 根据规则生成code
     * @return String
     */
    private static String getCodeFromRule(NumSplicingRule rule) {
        switch (rule) {
            case DEFAULT: {
                break;
            }
            case RANDOM: {
                return RandomStringUtils.randomNumeric(2);
            }
            case REDIS_INCR: {
                // TODO 以后再加
                return RandomStringUtils.randomNumeric(2);
            }
            case TIMESTAMP: {
                return DateUtils.getCurrentTimestampStr();
            }
        }
        return null;
    }

}
