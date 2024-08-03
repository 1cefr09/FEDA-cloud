package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostDTO implements Serializable {
    private Long id;

    private String title;

    private String content;

    private Long authorId;

    private Long categoryId;

    private boolean isBanned;
}
