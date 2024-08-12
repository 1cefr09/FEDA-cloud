package com.example.exception;

/**
 * 获取不到锁异常
 */
public class CantGetLockException extends BaseException{
    public CantGetLockException(){}
    public CantGetLockException(String msg){
        super(msg);
    }
}
