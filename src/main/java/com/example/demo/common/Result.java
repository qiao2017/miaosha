package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Result<T> {
    private Integer code;
    private T data;
    private String message;
}
