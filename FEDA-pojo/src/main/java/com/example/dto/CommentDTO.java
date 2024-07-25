package com.example.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private Long postId;

    private Long parentId;

    private String content;

    private Long authorId;
}
