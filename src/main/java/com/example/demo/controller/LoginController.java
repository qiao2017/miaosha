package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.common.SuccessResult;
import com.example.demo.service.UserService;
import com.example.demo.so.LoginUserSO;
import com.example.demo.so.RegisterUserSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/doLogin")
    public Result<String> doLogin(HttpServletResponse response, @RequestBody LoginUserSO loginUserSO){
        String token = userService.doLogin(response, loginUserSO);
        return new SuccessResult<>(token);
    }

    @PostMapping("/doRegister")
    public Result<String> doRegister(HttpServletResponse response, @RequestBody RegisterUserSO loginUserSO){
        String token = userService.doRegister(response, loginUserSO);
        return new SuccessResult<>(token);
    }
}
