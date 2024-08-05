package com.example.service.Impl;

import com.example.dto.CategoryListQueryDTO;
import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.example.result.PageResult;
import com.example.service.CategoryService;
import com.example.vo.CategoryVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> categoryListQuery() {

        List<CategoryVO> categories = categoryMapper.categoryListQuery();

        return categories;
    }
}
