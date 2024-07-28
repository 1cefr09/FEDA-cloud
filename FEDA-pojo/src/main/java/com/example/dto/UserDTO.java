package com.example.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 仿照苍穹外卖这里直接设置了一个code属性，可能是用于登陆的
 */
@Data
public class UserDTO implements Serializable {

    private String Username;

    private String password;

    private String passwordRepeat;

    private String email;
}
