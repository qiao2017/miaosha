package com.example.demo.service;

import com.example.demo.so.LoginUserSO;
import com.example.demo.so.RegisterUserSO;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    String doLogin(HttpServletResponse response, LoginUserSO loginUserSO);

    String doRegister(HttpServletResponse response, RegisterUserSO loginUserSO);
}
