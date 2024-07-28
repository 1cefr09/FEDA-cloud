package com.example.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 未完成
 */
@Data
public class UserVO implements Serializable {
    private Long id;

    private String Username;

    private String password;

    private String role;

    private String email;

}
