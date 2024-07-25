package com.example.vo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommentVO {
    private Long id;

    private Long postId;

    private Long parentId;

    private String content;

    private Long authorId;

    private LocalDateTime createTime;
}
