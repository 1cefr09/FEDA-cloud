package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 发帖实体类
 */
//es中需要用注解来用于定义和控制ElasticSearch中索引字段的行为：
//
//@Id 注解：用于标识实体的唯一标识符。在ElasticSearch中，它指定了文档的唯一ID。
//
//@Field 注解：用于定义字段在ElasticSearch中的映射，包括字段类型、分析器等。这允许你控制如何存储和索引字段数据，例如使用 FieldType.Text 来指定字段的文本类型，并可以设置分析器来影响文本分析和搜索。
@Data
public class Post {
    private Long id;

    private String title;

    private String content;

    private Long authorId;

    private String authorName;

    private Long categoryId;

    private boolean isBanned = false;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
