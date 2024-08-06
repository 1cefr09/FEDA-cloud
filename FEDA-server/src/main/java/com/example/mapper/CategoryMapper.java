package com.example.mapper;

import com.example.dto.CategoryListQueryDTO;
import com.example.entity.Category;
import com.example.vo.CategoryVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //
    List<CategoryVO> categoryListQuery();

    long getCategoryIdByName(String categoryName);

}
