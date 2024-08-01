package com.example.handler;

import com.example.constant.MessageConstant;
import com.example.exception.BaseException;
import com.example.exception.UsernameAlreadyExistException;
import com.example.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，用来处理项目中抛出的业务异常并且返回给前端
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     *处理用户名重复的异常
     * @param ex
     * @return
     */
    @ExceptionHandler(UsernameAlreadyExistException.class)
    public Result handleUsernameAlreadyExistException(UsernameAlreadyExistException ex){
        String message = ex.getMessage();
        return Result.error(message+ MessageConstant.USERNAME_EXIST);

    }
}
