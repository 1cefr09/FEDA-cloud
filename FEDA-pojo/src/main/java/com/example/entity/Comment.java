package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
public class Comment {
    private Long id;

    private Long postId;

    private Long parentId;

    private String content;

    private Long authorId;

    private LocalDateTime createTime;
    //1231231git add .

}
