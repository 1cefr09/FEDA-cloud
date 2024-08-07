package com.example.dto;

import lombok.Data;

@Data
public class CommentPageQueryDTO {
    private int page;

    private int pageSize;

    private Long postId;
}
