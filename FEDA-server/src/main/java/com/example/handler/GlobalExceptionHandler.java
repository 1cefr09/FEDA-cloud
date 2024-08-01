package com.example.handler;

import com.example.constant.MessageConstant;
import com.example.exception.AccountNotFoundException;
import com.example.exception.BaseException;
import com.example.exception.PasswordErrorException;
import com.example.exception.UsernameAlreadyExistException;
import com.example.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，用来处理项目中抛出的业务异常并且返回给前端
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


//    @ExceptionHandler(BaseException.class)
//    public Result handleBaseException(BaseException ex){
//        String message = ex.getMessage();
//        return Result.error(message);
//    }
    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<Result> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Result> handleAccountNotFoundException(AccountNotFoundException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordErrorException.class)
    public ResponseEntity<Result> handlePasswordErrorException(PasswordErrorException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
