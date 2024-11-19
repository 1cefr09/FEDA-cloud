package com.example.controller.web;

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
 * Controller for handling comment-related actions.
 * 跟帖相关操作控制器
 */
@RestController
@RequestMapping("/api/comment")
@Slf4j
@Api(tags = "回帖相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Allows a user to comment on a post.
     * 用户对帖子进行评论
     *
     * @param commentDTO the comment data transfer object 评论数据传输��象
     * @return the result of the operation 操作结果
     */
    @PostMapping("/userComment")
    @ApiOperation(value = "回帖接口")
//    @CheckBanStatus
    public Result userComment(@RequestBody CommentDTO commentDTO) {
        long userId = BaseContext.getCurrentId();
        commentDTO.setAuthorId(userId);
        log.info("回帖{}", commentDTO);
        commentService.userComment(commentDTO);
        return Result.success();
    }

    /**
     * Retrieves a paginated list of comments for a post.
     * 获取帖子的分页评论列表
     *
     * @param page the page number 页码
     * @param pageSize the size of the page 页大小
     * @param postId the ID of the post 帖子ID
     * @return the result containing the paginated list of comments 包含分页评论列表的结果
     */
    @GetMapping("/commentPage")
    @ApiOperation("comment分页查询")
    public Result<PageResult> commentPage(
            @RequestParam int page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam long postId) {

        CommentPageQueryDTO commentPageQueryDTO = new CommentPageQueryDTO();
        commentPageQueryDTO.setPage(page);
        if (pageSize != null) {
            commentPageQueryDTO.setPageSize(pageSize);
        }
        commentPageQueryDTO.setPostId(postId);

        log.info("comment查询，参数为：{}", commentPageQueryDTO);
        PageResult commentPageResult = commentService.commentPageQuery(commentPageQueryDTO);
        return Result.success(commentPageResult);
    }
}