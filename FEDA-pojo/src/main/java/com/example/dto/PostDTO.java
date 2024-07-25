package com.example.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;

    private String title;

    private String content;

    private Long authorId;
}
