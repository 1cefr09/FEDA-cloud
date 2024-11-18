package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryListQueryDTO implements Serializable {

    private int page = 1;

    private int pageSize = 100;


}
