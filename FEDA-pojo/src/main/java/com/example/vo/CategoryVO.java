package com.example.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryVO {

    private Long id;

    private String categoryName;

    private boolean isBanned = false;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
