package com.example.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultStatus {
    /**
     * 成功
     */
    SUCCESS(0, "请求成功"),

    /**
     * 40XX登录相关
     */
    ACCESS_CHECK_FAILURE(4000, "用户未登录");
    ;
    private Integer code;
    private String message;
}
