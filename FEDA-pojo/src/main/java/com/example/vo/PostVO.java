package com.example.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class PostVO implements Serializable {
    private Long id;

    private String title;

    private String content;

    private Long authorId;

    private LocalDateTime createTime;
}
