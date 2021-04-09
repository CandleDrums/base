/**
 * @Project base-redis
 * @Package com.cds.base.redis.client
 * @Class RedisClient.java
 * @Date Oct 10, 2019 2:39:06 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.redis.client;

import org.redisson.api.RedissonClient;

/**
 * @Description Redis 客户端
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 10, 2019 2:39:06 PM
 * @version 1.0
 * @since JDK 1.8
 */
public interface RedisClient<T> extends RedissonClient {

}
