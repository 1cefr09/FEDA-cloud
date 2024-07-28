package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
public class Comment {
    private Long id;

    private Long adminId;

    private String actionType;

    private Long targetId;

    private LocalDateTime timestamp;

}
