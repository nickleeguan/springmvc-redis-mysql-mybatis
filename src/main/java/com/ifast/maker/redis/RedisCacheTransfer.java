package com.ifast.maker.redis;

/**
 * Created by 李官宇 on 2017/7/28.
 * It comes to a very simple choose,really,
 * get busy living,or get busy dying.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * 创建中间类RedisCacheTransfer，完成RedisCache.jedisConnectionFactory的静态注入
 */
public class RedisCacheTransfer {
    @Autowired
    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
    }
}
