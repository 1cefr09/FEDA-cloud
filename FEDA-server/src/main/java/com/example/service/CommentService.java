package com.example.service;


import com.example.dto.CommentDTO;
import com.example.dto.CommentPageQueryDTO;
import com.example.entity.Comment;
import com.example.result.PageResult;
import org.springframework.web.bind.annotation.RestController;

public interface CommentService {
    /**
     * 用户回帖
     * @param commentDTO
     * @return
     */
    Comment userComment(CommentDTO commentDTO);

    /**
     * comment回帖查询
     * @param commentPageQueryDTO
     * @return
     */
    PageResult commentPageQuery(CommentPageQueryDTO commentPageQueryDTO);
}
