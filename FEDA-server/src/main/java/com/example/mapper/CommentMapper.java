package com.example.mapper;

import com.example.dto.CommentPageQueryDTO;
import com.example.entity.Comment;
import com.example.entity.Post;
import com.example.vo.CommentVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

    /**
     * 插入数据
     * @param comment
     */
    void insert(Comment comment);

    Boolean getIsBanned(long id);

    void updateCommentBanned(@Param("targetId") long targetId,@Param("commentIsBanned") boolean commentIsBanned);

    void updateUsername(@Param("Id") long Id, @Param("username") String username);
    /**
     * comment回帖查询
     * @param commentPageQueryDTO
     * @return
     */
    Page<CommentVO> commentPageQuery(CommentPageQueryDTO commentPageQueryDTO);

    long getAuthorIdByCommentId(long commentId);
}
