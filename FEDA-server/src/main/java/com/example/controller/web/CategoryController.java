package com.example.controller.web;


import com.example.dto.CategoryListQueryDTO;
import com.example.dto.PostPageQueryDTO;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Slf4j
@Api(tags = "板块相关接口")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categoryList")
    @ApiOperation("板块列表")
    public Result<List> categoryList() {

        // 创建 DTO 对象
//        CategoryListQueryDTO categoryListQueryDTO = new CategoryListQueryDTO();
//        categoryListQueryDTO.setPage(page);
//        categoryListQueryDTO.setPageSize(pageSize);
        log.info("查询板块列表");
        List categoryListResult = categoryService.categoryListQuery();
        return Result.success(categoryListResult);
    }

}
