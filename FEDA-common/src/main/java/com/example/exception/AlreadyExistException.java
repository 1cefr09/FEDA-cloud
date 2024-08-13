package com.example.exception;

/**
 * 用户名重复异常
 */
public class AlreadyExistException extends BaseException {


    public AlreadyExistException() {
    }

    public AlreadyExistException(String msg) {
        super(msg);
    }

}
