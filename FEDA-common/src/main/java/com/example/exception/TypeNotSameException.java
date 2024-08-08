package com.example.exception;

/**
 * 操作类型不一致异常
 */
public class TypeNotSameException extends BaseException{
    public TypeNotSameException(){

    }

    public TypeNotSameException(String msg){
        super(msg);
    }
}
