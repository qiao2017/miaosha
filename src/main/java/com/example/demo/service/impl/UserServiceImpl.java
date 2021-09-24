package com.example.demo.service.impl;

import com.example.demo.bean.User;
import com.example.demo.constant.RedisKeyConstant;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.so.LoginUserSO;
import com.example.demo.so.RegisterUserSO;
import com.example.demo.util.JedisUtil;
import com.example.demo.util.RedisKeyUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisUtil jedisUtil;
    @Override
    public String doLogin(HttpServletResponse response, LoginUserSO loginUserSO) {
        User user = userMapper.getUserByUserName(loginUserSO.getUserName());
        if (user == null){
            throw new BusinessException("用户名或密码错误");
        }
        String requestPassword = DigestUtils.md5Hex(DigestUtils.md5Hex(loginUserSO.getPassword()) + user.getSalt());
        if (!Objects.equals(requestPassword, user.getPassword())){
            throw new BusinessException("用户名或密码错误");
        }
        // 下发token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        response.setHeader("token", token);
        jedisUtil.setEx(RedisKeyUtil.getTokenKey(token), token, RedisKeyConstant.EXPIRE_TIME);

        // 异步处理
        jedisUtil.sadd(RedisKeyUtil.getUserIdKey(user.getId()), token);
        jedisUtil.expire(RedisKeyUtil.getUserIdKey(user.getId()), RedisKeyConstant.EXPIRE_TIME);
        Set<String> tokenSet = jedisUtil.smembers(RedisKeyUtil.getUserIdKey(user.getId()));
        tokenSet.removeIf(tokenStr -> jedisUtil.exists(RedisKeyUtil.getTokenKey(tokenStr)));
        if (CollectionUtils.isNotEmpty(tokenSet)){
            jedisUtil.srem(RedisKeyUtil.getUserIdKey(user.getId()), tokenSet.toArray(new String[]{}));
        }
        return token;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String doRegister(HttpServletResponse response, RegisterUserSO userSO) {
        User user = new User();
        user.setUserName(userSO.getUserName());
        user.setSalt(UUID.randomUUID().toString().substring(24, 34));
        user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(userSO.getPassword()) + user.getSalt()));
        userMapper.insertUser(user);
        LoginUserSO loginUserSO = new LoginUserSO();
        loginUserSO.setPassword(userSO.getPassword());
        loginUserSO.setUserName(userSO.getUserName());
        return doLogin(response, loginUserSO);
    }
}
