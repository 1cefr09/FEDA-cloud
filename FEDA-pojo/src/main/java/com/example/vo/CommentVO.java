package com.example.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class CommentVO implements Serializable {
    private Long id;

    private Long postId;

    private Long parentId;

    private String content;

    private Long authorId;

    private String authorName;

    private Long floor;

    private LocalDateTime createTime;
}
