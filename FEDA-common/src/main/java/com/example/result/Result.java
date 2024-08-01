package com.example.result;

import lombok.Data;

/**
 * 后端返回结果
 * @param <T>
 */
@Data
public class Result<T> {
    private Integer code;   //响应码，1为成功，0和其他数字均为失败
    private String message;  //错误信息
    private T data;  //数据

    //成功且无数据返回的情况
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    ////成功且有数据返回的情况
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    //失败的情况
    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.message = msg;
        result.code = 0;
        return result;
    }
}
