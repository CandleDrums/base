/**
 * @Project base-common
 * @Package com.cds.base.common.annotaion
 * @Class NumGenerateRule.java
 * @Date Sep 4, 2020 6:10:58 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.common.annotaion;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @Description 编号（业务主键）生成规则
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 4, 2020 6:10:58 PM
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface NumGenerateRule {
    /**
     * @description 前缀
     * @return String
     */
    String prefixCode();

    /**
     * @description 规则码
     * @return int
     */
    int ruleCode() default 7;

}
