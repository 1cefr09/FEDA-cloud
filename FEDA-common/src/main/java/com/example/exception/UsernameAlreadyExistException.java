package com.example.exception;

/**
 * 用户名重复异常
 */
public class UsernameAlreadyExistException extends BaseException {


    public UsernameAlreadyExistException() {
    }

    public UsernameAlreadyExistException(String msg) {
        super(msg);
    }

}
