package com.example.controller.web;

import com.example.service.CommentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 跟帖
 */
@RestController
@RequestMapping("/api/comment")
@Slf4j
@Api(tags = "回帖相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;



}
