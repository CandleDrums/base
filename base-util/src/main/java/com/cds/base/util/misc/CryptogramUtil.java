/*
 * Copyright 2013-2015 duolabao.com All Right Reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.cds.base.util.misc;

import java.util.Random;

/**
 * 类CryptogramUtil.java的实现描述：密码工具类
 * 
 * @author george 2015年11月20日 下午6:42:38
 */
public class CryptogramUtil {

    /**
     * 生成固定长度的随机数字密码
     * 
     * @param length 小于零返回空串
     * @return
     * @throws Exception
     */
    public static String generalRandomDigital(int length) {
        if (length < 0) {
            return "";
        }
        Random random = new Random();
        StringBuilder randoms = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randoms.append(random.nextInt(10));
        }
        return randoms.toString();
    }
}
