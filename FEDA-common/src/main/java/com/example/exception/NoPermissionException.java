package com.example.exception;

/**
 * 用户无权限
 */

public class NoPermissionException extends BaseException {
    public NoPermissionException(){}

    public NoPermissionException(String msg){
        super(msg);
    }
}
