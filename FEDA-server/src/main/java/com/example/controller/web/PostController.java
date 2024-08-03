package com.example.controller.web;

import com.example.context.BaseContext;
import com.example.dto.PostDTO;
import com.example.result.Result;
import com.example.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
