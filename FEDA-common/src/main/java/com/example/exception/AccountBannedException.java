package com.example.exception;

/**
 * 账号被锁定异常
 */
public class AccountBannedException extends BaseException {

    public AccountBannedException() {
    }

    public AccountBannedException(String msg) {
        super(msg);
    }

}
