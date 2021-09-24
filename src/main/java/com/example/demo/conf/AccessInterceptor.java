package com.example.demo.conf;

import com.alibaba.fastjson.JSON;
import com.example.demo.access.UserContext;
import com.example.demo.bean.LoginUser;
import com.example.demo.common.FailureResult;
import com.example.demo.constant.RedisKeyConstant;
import com.example.demo.exception.AccessException;
import com.example.demo.util.JedisUtil;
import com.example.demo.util.RedisKeyUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

@Component
public class AccessInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisUtil jedisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getTokenFromRequest(request);
        if (StringUtils.isEmpty(token)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        String userStr = jedisUtil.get(token);
        if (StringUtils.isBlank(userStr)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            render(response);
            return false;
        }
        LoginUser user = JSON.parseObject(userStr, LoginUser.class);
        UserContext.setUser(user);
        Random random = new Random();
        if(random.nextBoolean()){
            jedisUtil.expire(token, RedisKeyConstant.EXPIRE_TIME);
            jedisUtil.expire(RedisKeyUtil.getUserIdKey(user.getId()), RedisKeyConstant.EXPIRE_TIME);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContext.removeUser();
    }

    private void render(HttpServletResponse response)throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str  = JSON.toJSONString(FailureResult.build(new AccessException("登录已过期")));
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

    /**
     * 从请求中获取token
     */
    private String getTokenFromRequest(HttpServletRequest request){
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)){
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "token")){
                    return cookie.getValue();
                }
            }
        }
        token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)){
            return token;
        }
        return request.getParameter("token");
    }
}
