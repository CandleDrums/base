/**
 * @Project base-util
 * @Package com.cds.base.util.generator.num
 * @Class NumGenerator.java
 * @Date 2019年9月16日 下午5:32:31
 * @Copyright (c) 2019 CandleDrums.com All Right Reserved.
 */
package com.cds.base.util.generator.num;

import org.apache.commons.lang3.RandomStringUtils;

import com.cds.base.common.rule.NumRule;
import com.cds.base.common.rule.NumSplicingRule;
import com.cds.base.util.misc.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description Num生成器
 * @Notes 未填写备注
 * @author maoshou
 * @Date 2019年9月16日 下午5:32:31
 * @version 1.0
 * @since JDK 1.8
 */
@Slf4j
public class NumGenerator {

    /**
     * @description 示例
     * @return void
     */
    public static void main(String[] args) {
        log.info(nextNum(NumRule.Account));
    }

    /**
     * @description 根据DO直接生成
     * @notes 注意：在<code>NumRule</code>必须已经声明与DO同名的枚举值
     * @return String
     */
    public static String nextNum(Class t) {
        // eg:AccountDO,去掉DO后为Account，与NumRule中的枚举名对应
        String className = t.getSimpleName();
        if (className.lastIndexOf("DO") <= 0 || className.lastIndexOf("VO") <= 0) {
            return "";
        }
        // 去掉DO/VO后缀
        className = className.substring(0, className.length() - 2);
        NumRule rule = NumRule.valueOf(className);
        StringBuffer sb = new StringBuffer();
        sb.append(rule.getPrefixCode());
        int splicingRule = rule.getNumSplicingRule();
        return generateCode(splicingRule, 1, sb);
    }

    /**
     * @description 获取编号
     * @return String
     */
    public static String nextNum(NumRule rule) {
        StringBuffer sb = new StringBuffer();
        sb.append(rule.getPrefixCode());
        int splicingRule = rule.getNumSplicingRule();
        return generateCode(splicingRule, 1, sb);
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
