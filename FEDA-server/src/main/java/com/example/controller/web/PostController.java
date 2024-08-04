package com.example.controller.web;

import com.example.context.BaseContext;
import com.example.dto.PostDTO;
import com.example.dto.PostPageQueryDTO;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 发帖
 */
@RestController
@RequestMapping("/api/post")
@Slf4j
@Api(tags = "发帖相关接口")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/userPost")
    @ApiOperation(value = "发帖接口")
    Result userPost(@RequestBody PostDTO postDTO) {
        long userId = BaseContext.getCurrentId();
        postDTO.setAuthorId(userId);
        log.info("发帖{}", postDTO);
        postService.userPost(postDTO);
        return Result.success();
    }

    @GetMapping("/postPage")
    @ApiOperation("post分页查询")
    public Result<PageResult> postPage(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(required = false) Long categoryId) {

        // 创建 DTO 对象
        PostPageQueryDTO postPageQueryDTO = new PostPageQueryDTO();
        postPageQueryDTO.setPage(page);
        postPageQueryDTO.setPageSize(pageSize);
        postPageQueryDTO.setCategoryId(categoryId);
        log.info("post分页查询，参数为：{}",postPageQueryDTO);
        PageResult postPageResult = postService.postPageQuery(postPageQueryDTO);
        return Result.success(postPageResult);
    }

}
