package com.example.controller.web;

import com.example.annotation.CheckBanStatus;
import com.example.context.BaseContext;
import com.example.dto.CommentDTO;
import com.example.dto.CommentPageQueryDTO;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/userComment")
    @ApiOperation(value = "回帖接口")
    @CheckBanStatus
    Result userComment(@RequestBody CommentDTO commentDTO){
        long userId = BaseContext.getCurrentId();
        commentDTO.setAuthorId(userId);
        log.info("回帖{}",commentDTO);
        commentService.userComment(commentDTO);
        return Result.success();
    }

    @GetMapping("/commentPage")
    @ApiOperation("comment分页查询")
    public Result<PageResult> commentPage(
            @RequestParam int page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam long postId) {

        CommentPageQueryDTO commentPageQueryDTO = new CommentPageQueryDTO();
        commentPageQueryDTO.setPage(page);
        if (pageSize != null){
            commentPageQueryDTO.setPageSize(pageSize);
        }
        commentPageQueryDTO.setPostId(postId);

        log.info("comment查询，参数为：{}",commentPageQueryDTO);
        PageResult commentPageResult = commentService.commentPageQuery(commentPageQueryDTO);
        return Result.success(commentPageResult);
    }



}
