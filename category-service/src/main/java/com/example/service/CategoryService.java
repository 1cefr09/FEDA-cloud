package com.example.service;

import com.example.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    List<CategoryVO> categoryListQuery();

    CategoryVO getCategoryById(Long id);

    CategoryVO getCategoryByName(String categoryName);
}
