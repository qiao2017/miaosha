package com.example.demo.common;

import com.example.demo.common.enums.ResultStatus;
import com.example.demo.exception.AccessException;

public class FailureResult<T> extends Result<T>{
    public FailureResult(Integer code, T data, String message){
        super(code, data, message);
    }
    public static FailureResult<String> build(ResultStatus status){
        return new FailureResult<String>(status.getCode(), null, status.getMessage());
    }
    public static FailureResult<String> build(AccessException exception){
        return new FailureResult<String>(exception.getCode(), null, exception.getMessage());
    }
}
