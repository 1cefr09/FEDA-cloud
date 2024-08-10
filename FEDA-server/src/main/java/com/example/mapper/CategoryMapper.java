package com.example.mapper;

import com.example.dto.CategoryListQueryDTO;
import com.example.entity.Category;
import com.example.vo.CategoryVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //
    List<CategoryVO> categoryListQuery();

    long getCategoryIdByName(String categoryName);

    void insert(Category category);

    boolean getIsBannedById(Long Id);

    void updateCategoryBanned(@Param("targetId") long targetId, @Param("categoryIsBanned") boolean categoryIsBanned);

}
