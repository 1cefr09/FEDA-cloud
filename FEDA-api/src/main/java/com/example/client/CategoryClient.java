package com.example.client;

import com.example.result.Result;
import com.example.vo.CategoryVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("category-service")
public interface CategoryClient {

    @GetMapping("/api/category/categoryList")
    Result<List> categoryList();

    @GetMapping("/api/category/getCategoryById")
    Result<CategoryVO> getCategoryById( @RequestParam Long id);

    @GetMapping("/api/category/getCategoryByName")
    Result<CategoryVO> getCategoryByName( @RequestParam String categoryName);

}
