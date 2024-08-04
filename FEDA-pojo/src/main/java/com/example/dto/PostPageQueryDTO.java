package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostPageQueryDTO implements Serializable {

    private int page;

    private int pageSize;

    //板块名称
    private String categoryName;

    //模块id
//    private Long id;
}
