package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostPageQueryDTO implements Serializable {

    private int page;

    private int pageSize = 10;

    //板块名称
    private String categoryName;

    //板块id
    private Long categoryId;
}
