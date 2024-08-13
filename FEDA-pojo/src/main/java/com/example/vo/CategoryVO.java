package com.example.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CategoryVO implements Serializable {

    private Long id;

    private String categoryName;

    private boolean isBanned = false;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
