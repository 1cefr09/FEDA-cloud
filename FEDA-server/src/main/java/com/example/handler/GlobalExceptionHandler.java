package com.example.handler;

import com.example.exception.*;
import com.example.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，用来处理项目中抛出的业务异常并且返回给前端
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(AlreadyExistException.class)//处理用户名已存在异常
    public ResponseEntity<Result> handleUsernameAlreadyExistException(AlreadyExistException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(AccountNotFoundException.class)//处理账号不存在异常
    public ResponseEntity<Result> handleAccountNotFoundException(AccountNotFoundException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordErrorException.class)//处理密码错误异常
    public ResponseEntity<Result> handlePasswordErrorException(PasswordErrorException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtParseFailedException.class)//处理jwt解析失败异常
    public ResponseEntity<Result> handleJwtParseFailedException(JwtParseFailedException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountBannedException.class)//处理账户被ban异常
    public ResponseEntity<Result> handleAccountBannedException(AccountBannedException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ParamErrorException.class)//处理参数错误异常
    public ResponseEntity<Result> handleParamErrorException(ParamErrorException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeNotSameException.class)//处理类型不一致异常
    public ResponseEntity<Result> handleTypeNotSameException(TypeNotSameException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoPermissionException.class)//处理无权限异常
    public ResponseEntity<Result> handleNoPermissionException(NoPermissionException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MailSendException.class)//邮件发送异常
    public ResponseEntity<Result> handleMailSendException(MailSendException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BaseException.class)//处理其它异常
    public ResponseEntity<Result> handleBaseException(BaseException ex){
        String message = ex.getMessage();
        Result result = Result.error(message);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
