package com.example.mapper;

import com.example.dto.CommentPageQueryDTO;
import com.example.entity.Comment;
import com.example.entity.Post;
import com.example.vo.CommentVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    /**
     * 插入数据
     * @param comment
     */
    void insert(Comment comment);


    /**
     * comment回帖查询
     * @param commentPageQueryDTO
     * @return
     */
    Page<CommentVO> commentPageQuery(CommentPageQueryDTO commentPageQueryDTO);
}
