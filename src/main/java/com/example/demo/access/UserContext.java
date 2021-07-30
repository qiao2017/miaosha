package com.example.demo.access;

import com.example.demo.bean.LoginUser;
import com.example.demo.exception.AccessException;

public class UserContext {
    private static final ThreadLocal<LoginUser> userHolder = new ThreadLocal<>();

    public static LoginUser getUser(){
        LoginUser user = userHolder.get();
        if (user == null){
            throw new AccessException("登录已失效");
        }
        return user;
    }

    public static void setUser(LoginUser user){
        userHolder.set(user);
    }

    public static void removeUser(){
        userHolder.remove();
    }
}
