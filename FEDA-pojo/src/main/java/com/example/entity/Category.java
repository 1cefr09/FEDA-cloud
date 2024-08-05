package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {

    private Long id;

    private String categoryName;

    private boolean isBanned = false;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
