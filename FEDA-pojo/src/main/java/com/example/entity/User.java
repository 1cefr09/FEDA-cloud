package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    private Long id;

    private String Username;

    private String password;

    private String email;

    private String role = "USER";

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
