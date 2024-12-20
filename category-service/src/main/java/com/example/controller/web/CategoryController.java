package com.example.controller.web;

import com.example.result.Result;
import com.example.service.CategoryService;
import com.example.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling category-related actions.
 * 板块相关操作控制器
 */
@RestController
@RequestMapping("/api/category")
@Slf4j
@Api(tags = "板块相关接口")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * Retrieves the list of categories.
     * 获取板块列表
     *
     * @return the result containing the list of categories 包含板块列表的结果
     */
    @GetMapping("/categoryList")
    @ApiOperation("板块列表")
    public Result<List> categoryList() {
        log.info("查询板块列表");
        List categoryListResult = categoryService.categoryListQuery();
        return Result.success(categoryListResult);
    }


    @GetMapping("/getCategoryById")
    @ApiOperation("根据id获取板块")
    public Result<CategoryVO> getCategoryById(Long id){
        log.info("根据id获取板块:{}",id);
        return Result.success(categoryService.getCategoryById(id));
    }

    @GetMapping("/getCategoryByName")
    @ApiOperation("根据name获取板块")
    public Result<CategoryVO> getCategoryByName(String categoryName){
        log.info("根据name获取板块:{}",categoryName);
        return Result.success(categoryService.getCategoryByName(categoryName));
    }

}