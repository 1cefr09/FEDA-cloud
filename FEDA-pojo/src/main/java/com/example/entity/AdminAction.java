package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminAction {
    private Long id;

    private Long postId;

    private Long parentId;

    private String content;

    private Long authorId;

    private LocalDateTime createTime;
}
