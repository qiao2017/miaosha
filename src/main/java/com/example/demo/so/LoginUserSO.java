package com.example.demo.so;

import lombok.Data;

@Data
public class LoginUserSO {
    private String userName;
    private String password;
    private String verificationCode;
}
