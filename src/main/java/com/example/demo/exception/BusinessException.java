package com.example.demo.exception;

import com.example.demo.common.enums.ResultStatus;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private Integer code;
    private String message;
    public BusinessException(Integer code, String message){

    }
    public BusinessException(String message){
        this.code = ResultStatus.DEFAULT_FAILURE.getCode();
        this.message = message;
    }
    public BusinessException(Integer code){

    }
    public BusinessException(){

    }
}
