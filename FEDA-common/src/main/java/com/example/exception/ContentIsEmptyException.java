package com.example.exception;

/**
 * 内容为空异常
 */
public class ContentIsEmptyException extends BaseException{
    public ContentIsEmptyException(){}

    public ContentIsEmptyException(String msg){
        super(msg);
    }
}
