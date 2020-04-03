/**
 * @Project base.util
 * @Package com.cds.base.util
 * @Class RegexUtils.java
 * @Date 2016年8月17日 下午6:05:22
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.util.misc;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description 正则工具类
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月17日 下午6:05:22
 * @version 1.0
 * @since JDK 1.8
 */
public class RegexUtils {

    /**
     * @description 核心判断
     * @param text
     * @param reg
     * @return
     * @returnType boolean
     * @author liming
     */
    public static boolean match(String text, String reg) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(reg)) {
            return false;
        }
        return Pattern.compile(reg).matcher(text).matches();
    }

}
