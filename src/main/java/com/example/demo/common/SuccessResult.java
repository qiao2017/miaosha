package com.example.demo.common;

import com.example.demo.common.enums.ResultStatus;

public class SuccessResult<T> extends Result<T>{
    public SuccessResult(T data, String message) {
        super(ResultStatus.SUCCESS.getCode(), data, message);
    }
    public SuccessResult(T data) {
        super(ResultStatus.SUCCESS.getCode(), data, null);
    }
    public SuccessResult() {
        super(ResultStatus.SUCCESS.getCode(), null, null);
    }
}
