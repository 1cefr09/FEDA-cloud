package com.example.controller.web;

import com.example.service.PostService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发帖
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
@Api(tags = "发帖相关接口")
public class PostController {
}
