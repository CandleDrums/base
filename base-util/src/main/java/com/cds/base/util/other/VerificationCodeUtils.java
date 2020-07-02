/**
 * @Project base-util
 * @Package com.cds.base.util.other
 * @Class VerificationCodeUtils.java
 * @Date Jul 2, 2020 3:21:57 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.util.other;

import com.cds.base.util.lang.RandomStringUtils;

/**
 * @Description 验证码工具
 * @Notes 未填写备注
 * @author liming
 * @Date Jul 2, 2020 3:21:57 PM
 */
public class VerificationCodeUtils {

    /**
     * @description 生成随机验证码
     * @return String
     */
    public static String generate(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
