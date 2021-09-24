package com.example.demo.exception;

import com.example.demo.common.FailureResult;
import com.example.demo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(value = AccessException.class)
    public Result<String> accessException(AccessException exception){
        return FailureResult.build(exception);
    }
    @ExceptionHandler(value = BusinessException.class)
    public Result<String> businessException(BusinessException exception){
        return FailureResult.build(exception);
    }
    @ExceptionHandler(value = Exception.class)
    public Result<String> exception(Exception exception){
        return FailureResult.build(exception.getMessage());
    }
}
