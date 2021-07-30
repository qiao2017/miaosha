package com.example.demo.exception;

import com.example.demo.common.enums.ResultStatus;
import lombok.Getter;

@Getter
public class AccessException extends RuntimeException{
    private Integer code;
    private String message;
    public AccessException(String message){
        this.code = ResultStatus.ACCESS_CHECK_FAILURE.getCode();
        this.message = message;
    }
    public AccessException(){
        this.code = ResultStatus.ACCESS_CHECK_FAILURE.getCode();
        this.message = ResultStatus.ACCESS_CHECK_FAILURE.getMessage();
    }
}
