package com.example.demo.util;

public class RedisKeyUtil {
    public static String getTokenKey(String token){
        return String.format("token:%s", token);
    }

    public static String getUserIdKey(Integer userId){
        return String.format("userId:%d", userId);
    }
}
