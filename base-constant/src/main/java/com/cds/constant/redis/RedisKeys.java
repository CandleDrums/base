/**
 * @Project base-constant
 * @Package com.cds.constant.redis
 * @Class RedisKeys.java
 * @Date Sep 29, 2019 6:38:55 PM
 * @Copyright (c) 2019 YOUWE All Right Reserved.
 */
package com.cds.constant.redis;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 29, 2019 6:38:55 PM
 * @version 1.0
 * @since JDK 1.8
 */
public class RedisKeys {

    public class Account {
        private static final String PREFIX = "<ACCOUNT>_";
        public static final String PREPARE_BALANCE_CHANGE_PREFIX = PREFIX + "PREPARE_BALANCE_CHANGE_";
    }

    public class Friendship {
        private static final String PREFIX = "<FRIENDSHIP>_";
    }
}
