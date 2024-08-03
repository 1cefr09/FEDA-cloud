package com.example.exception;

/**
 * 标题为空异常
 */
public class TitleIsEmptyException extends BaseException{
    public TitleIsEmptyException(){

    }
    public TitleIsEmptyException(String msg){
        super(msg);
    }
}
