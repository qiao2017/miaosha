package com.example.demo.util;

import com.example.demo.conf.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Set;

@Component
public class JedisUtil {
    @Autowired
    private JedisConfig jedisConfig;
    private JedisPool pool;

    @PostConstruct
    public void init(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(1);
        config.setMaxTotal(2);
        config.setTestOnCreate(true);
        pool = new JedisPool(config, jedisConfig.getHost(), jedisConfig.getPort());
    }

    public void set(String key, String value){
        Jedis jedis = pool.getResource();
        String result = jedis.set(key, value);
        jedis.close();
    }

    public void sadd(String key, String... member){
        Jedis jedis = pool.getResource();
        jedis.sadd(key, member);
        jedis.close();
    }

    public void srem(String key, String... member){
        Jedis jedis = pool.getResource();
        jedis.srem(key, member);
        jedis.close();
    }

    public Set<String> smembers(String key){
        Jedis jedis = pool.getResource();
        Set<String> members = jedis.smembers(key);
        jedis.close();
        return members;
    }

    public String get(String key){
        Jedis jedis = pool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public void setEx(String key, String value, long seconds){
        Jedis jedis = pool.getResource();
        String result = jedis.setex(key, seconds, value);
        jedis.close();
    }

    public void expire(String key, long seconds){
        Jedis jedis = pool.getResource();
        jedis.expire(key, seconds);
        jedis.close();
    }

    public Boolean exists(String key){
        Jedis jedis = pool.getResource();
        Boolean exists = jedis.exists(key);
        jedis.close();
        return exists;
    }

    @PreDestroy
    public void destroy(){
        pool.close();
    }
}
