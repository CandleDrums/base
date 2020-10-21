/**
 * @Project base-util
 * @Package com.cds.base.util.generator.num
 * @Class NumGenerator.java
 * @Date 2019年9月16日 下午5:32:31
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.generator.num;

import org.apache.commons.lang3.RandomStringUtils;

import com.cds.base.common.annotaion.handler.NumGenerateRuleHandler;
import com.cds.base.common.exception.BusinessException;
import com.cds.base.common.rule.NumRule;
import com.cds.base.common.rule.NumSplicingRule;
import com.cds.base.generator.exception.NumGeneratorException;
import com.cds.base.util.bean.BeanUtils;
import com.cds.base.util.bean.CheckUtils;
import com.cds.base.util.misc.DateUtils;

/**
 * @Description Num生成器
 * @Notes 未填写备注
 * @author maoshou
 * @Date 2019年9月16日 下午5:32:31
 * @version 1.0
 * @since JDK 1.8
 */
public class NumGenerator<VO> {

    public static final String CODE_1 = "10001";
    public static final String CODE_2 = "10001";

    public static <VO> String generateAndSetNum(VO vo) {
        Object numExtised = BeanUtils.getProperty(vo, "num");
        if (CheckUtils.isNotEmpty(numExtised)) {
            return numExtised.toString();
        }
        NumRule numRule = NumGenerateRuleHandler.getNumRule(vo.getClass());
        if (numRule == null) {
            throw new NumGeneratorException(CODE_1, "无效类型");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(numRule.getPrefixCode());
        String num = generateCode(numRule.getNumSplicingRule(), 1, sb);
        try {
            BeanUtils.setProperty(vo, "num", num);
            return num;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NumGeneratorException(CODE_2, "生成编号失败");
    }

    /**
     * @description 获取编号
     * @return String
     */
    public static <VO> String nextNum(VO vo) {
        NumRule numRule = NumGenerateRuleHandler.getNumRule(vo.getClass());
        if (numRule == null) {
            throw new BusinessException("无效类型");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(numRule.getPrefixCode());
        return generateCode(numRule.getNumSplicingRule(), 1, sb);
    }

    /**
     * @description 获取编号
     * @return String
     */
    public static String nextNum(Class clazz) {
        NumRule numRule = NumGenerateRuleHandler.getNumRule(clazz);
        if (numRule == null) {
            throw new BusinessException("无效类型");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(numRule.getPrefixCode());
        return generateCode(numRule.getNumSplicingRule(), 1, sb);
    }

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
