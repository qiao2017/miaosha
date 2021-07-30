package com.example.demo.util;

import com.example.demo.conf.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

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

    }

    public void set(String key, String value){

    }
}
