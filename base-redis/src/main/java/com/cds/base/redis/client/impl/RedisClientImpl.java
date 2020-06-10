/**
 * @Project base-redis
 * @Package com.cds.base.redis.client.impl
 * @Class RedisClientImpl.java
 * @Date Oct 10, 2019 2:40:24 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved
 */
package com.cds.base.redis.client.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.cds.base.redis.client.RedisClient;

/**
 * @Description redis实现
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 10, 2019 2:40:24 PM
 * @version 1.0
 * @since JDK 1.8
 */
@Component(value = "redisClient")
public class RedisClientImpl<T> implements RedisClient<T> {

    @Autowired
    private RedisTemplate redisTemplate;

    // =============================common============================
    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#expire(java.lang.String,
     * long)
     */
    @Override
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cds.starter.redis.client.impl.RedisClient#getExpire(java.lang.String)
     */
    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hasKey(java.lang.String)
     */
    @Override
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#del(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(getKeyList(keys));
            }
        }
    }

    // ============================String=============================
    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#get(java.lang.String)
     */
    @Override
    public T get(String key) {
        return key == null ? null : (T)redisTemplate.opsForValue().get(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#set(java.lang.String,
     * java.lang.T)
     */
    @Override
    public boolean set(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#set(java.lang.String,
     * java.lang.T, long)
     */
    @Override
    public boolean set(String key, T value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#incr(java.lang.String,
     * long)
     */
    @Override
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.RedisClient#incr(java.lang.String)
     */
    @Override
    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.RedisClient#decr(java.lang.String)
     */
    @Override
    public long decr(String key) {
        return redisTemplate.opsForValue().increment(key, -1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cds.starter.redis.client.RedisClient#incrWithExpire(java.lang.String,
     * long)
     */
    @Override
    public long incrWithExpire(String key, long expireSecond) {
        long count = this.incr(key);
        redisTemplate.expire(key, expireSecond, TimeUnit.SECONDS);
        return count;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#decr(java.lang.String,
     * long)
     */
    @Override
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================
    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hget(java.lang.String,
     * java.lang.String)
     */
    @Override
    public T hget(String key, String item) {
        return (T)redisTemplate.opsForHash().get(key, item);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hmget(java.lang.String)
     */
    @Override
    public Map<T, T> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hmset(java.lang.String,
     * java.util.Map)
     */
    @Override
    public boolean hmset(String key, Map<String, T> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hmset(java.lang.String,
     * java.util.Map, long)
     */
    @Override
    public boolean hmset(String key, Map<String, T> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hset(java.lang.String,
     * java.lang.String, java.lang.T)
     */
    @Override
    public boolean hset(String key, String item, T value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hset(java.lang.String,
     * java.lang.String, java.lang.T, long)
     */
    @Override
    public boolean hset(String key, String item, T value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hdel(java.lang.String,
     * java.lang.T)
     */
    @Override
    public void hdel(String key, T... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cds.starter.redis.client.impl.RedisClient#hHasKey(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hincr(java.lang.String,
     * java.lang.String, double)
     */
    @Override
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#hdecr(java.lang.String,
     * java.lang.String, double)
     */
    @Override
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================
    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#sGet(java.lang.String)
     */
    @Override
    public Set<T> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cds.starter.redis.client.impl.RedisClient#sHasKey(java.lang.String,
     * java.lang.T)
     */
    @Override
    public boolean sHasKey(String key, T value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#sSet(java.lang.String,
     * java.lang.T)
     */
    @Override
    public long sSet(String key, T... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cds.starter.redis.client.impl.RedisClient#sSetAndTime(java.lang.String,
     * long, java.lang.T)
     */
    @Override
    public long sSetAndTime(String key, long time, T... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cds.starter.redis.client.impl.RedisClient#sGetSetSize(java.lang.String)
     */
    @Override
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cds.starter.redis.client.impl.RedisClient#setRemove(java.lang.String,
     * java.lang.T)
     */
    @Override
    public long setRemove(String key, T... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================list=================================

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#lGet(java.lang.String,
     * long, long)
     */
    @Override
    public List<T> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#lGetListSize(java.lang.
     * String)
     */
    @Override
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cds.starter.redis.client.impl.RedisClient#lGetIndex(java.lang.String,
     * long)
     */
    @Override
    public T lGetIndex(String key, long index) {
        try {
            return (T)redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#lSet(java.lang.String,
     * java.lang.T)
     */
    @Override
    public boolean lSet(String key, T value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#lSet(java.lang.String,
     * java.lang.T, long)
     */
    @Override
    public boolean lSet(String key, T value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#lSet(java.lang.String,
     * java.util.List)
     */
    @Override
    public boolean lSet(String key, List<T> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#lSet(java.lang.String,
     * java.util.List, long)
     */
    @Override
    public boolean lSet(String key, List<T> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cds.starter.redis.client.impl.RedisClient#lUpdateIndex(java.lang.
     * String, long, java.lang.T)
     */
    @Override
    public boolean lUpdateIndex(String key, long index, T value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cds.starter.redis.client.impl.RedisClient#lRemove(java.lang.String,
     * long, java.lang.T)
     */
    @Override
    public long lRemove(String key, long count, T value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private List<String> getKeyList(String... keys) {
        List<String> result = new ArrayList<String>();
        for (String key : keys) {
            result.add(key);
        }
        return result;
    }

}
