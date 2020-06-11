/**
 * @Project base-util
 * @Package com.cds.base.util.generator.redis
 * @Class RedisKeyGenerator.java
 * @Date Sep 29, 2019 5:30:37 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.util.generator.redis;

/**
 * @Description Redis key 生成器
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 29, 2019 5:30:37 PM
 * @version 1.0
 * @since JDK 1.8
 */
public class RedisKeyGenerator {
    private final static String SPLIT_CHAR = "_";

    /**
     * @description 获取key
     * @return String
     */
    public static String getKey(String prefix, String value) {
        return prefix + "[" + value + "]";
    }

    /**
     * @description 拼接key
     * @return String
     */
    public static String appendKey(String... keys) {
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key);
            sb.append(SPLIT_CHAR);
        }
        // 去掉结尾下划线
        return sb.substring(0, sb.length() - 1).toString();
    }

}
