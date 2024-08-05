package com.example.service;

import com.example.dto.CategoryListQueryDTO;
import com.example.result.PageResult;
import com.example.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    List<CategoryVO> categoryListQuery();
}
