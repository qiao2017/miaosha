package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.so.LoginUserSO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/doLogin")
    public Result<Object> doLogin(HttpServletResponse response, LoginUserSO loginUserSO){
        return null;
    }
}
