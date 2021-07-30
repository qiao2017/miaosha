package com.example.demo.exception;

import com.example.demo.common.FailureResult;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = AccessException.class)
    public Result<String> accessException(AccessException exception){
        return FailureResult.build(exception);
    }
}
