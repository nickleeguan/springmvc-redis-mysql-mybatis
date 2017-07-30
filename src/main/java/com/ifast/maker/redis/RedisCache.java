package com.ifast.maker.redis;

/**
 * Created by 李官宇 on 2017/7/28.
 * It comes to a very simple choose,really,
 * get busy living,or get busy dying.
 */

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @描述: 使用第三方内存数据库Redis作为二级缓存
 */
public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private static JedisConnectionFactory jedisConnectionFactory;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id;

    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug("MybatisRedisCache:id=" + id);
        this.id = id;
    }

    public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.jedisConnectionFactory = jedisConnectionFactory;
    }

    public String getId() {
        return this.id;
    }

    public void putObject(Object key, Object value) {
        JedisConnection connection = null;
        try
        {
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
        }
        catch (JedisConnectionException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public Object getObject(Object key) {
        Object result = null;
        JedisConnection connection = null;
        try{
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            result = serializer.deserialize(connection.get(serializer.serialize(key)));
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.close();
            }
        }
        return result;
    }

    public Object removeObject(Object key) {
        JedisConnection connection = null;
        Object result = null;
        try
        {
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            result =connection.expire(serializer.serialize(key), 0);
        }
        catch (JedisConnectionException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    /**
     * 清空缓存
     */
    public void clear() {
        JedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            connection.flushDb();
            connection.flushAll();
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.close();
            }
        }
    }

    public int getSize() {
        int result = 0;
        JedisConnection connection = null;
        try
        {
            connection = jedisConnectionFactory.getConnection();
            result = Integer.valueOf(connection.dbSize().toString());
        }
        catch (JedisConnectionException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
