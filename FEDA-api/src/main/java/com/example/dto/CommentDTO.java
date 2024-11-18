package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentDTO implements Serializable {
    //private Long id;

    private Long postId;

    private Long parentId = 1L ;

    private String content;

    private Long authorId;
}
