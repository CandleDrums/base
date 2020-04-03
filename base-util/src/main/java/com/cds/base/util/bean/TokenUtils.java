/**
 * @Project common.util
 * @Package com.cds.common.util
 * @Class TokenUtils.java
 * @Date 2016年9月5日 下午4:53:04
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.util.bean;

import com.cds.base.util.misc.DateUtils;
import com.cds.base.util.misc.MD5Utils;
import com.cds.base.util.misc.RandomUtils;

/**
 * @Description token工具类
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年9月5日 下午4:53:04
 * @version 1.0
 * @since JDK 1.8
 */
public class TokenUtils {

    public static <T> String generateToken(T value) {
        if (CheckUtils.isEmpty(value)) return "";
        String objValue = JsonUtils.getJson(value);
        String exactTime = DateUtils.getCurrentSimpleExactTime();
        String randomStr = RandomUtils.randomString(16);
        return MD5Utils.md5(objValue + exactTime + randomStr);
    }

}
