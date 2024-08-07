package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.dto.CommentDTO;
import com.example.dto.CommentPageQueryDTO;
import com.example.entity.Comment;
import com.example.exception.ContentIsEmptyException;
import com.example.mapper.CommentMapper;
import com.example.mapper.PostMapper;
import com.example.mapper.UserMapper;
import com.example.result.PageResult;
import com.example.service.CommentService;
import com.example.vo.CommentVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户回帖
     * @param commentDTO
     * @return
     */
    @Transactional
    @Override
    public Comment userComment(CommentDTO commentDTO){
        System.out.println("回帖传入Service层的DTO" + commentDTO);
        //数据验证
        if (commentDTO.getContent() == null || commentDTO.getContent().isEmpty()){
            throw new ContentIsEmptyException(MessageConstant.CONTENT_EMPTY);
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        String authorName = userMapper.getUsernameById(commentDTO.getAuthorId());
        comment.setAuthorName(authorName);

        //保存实体到数据库
        commentMapper.insert(comment);
        postMapper.update(commentDTO.getPostId());
        //返回保存后的实体
        return comment;

    }

    @Override
    public PageResult commentPageQuery(CommentPageQueryDTO commentPageQueryDTO){

        PageHelper.startPage(commentPageQueryDTO.getPage(),commentPageQueryDTO.getPageSize());
        Page<CommentVO> page = commentMapper.commentPageQuery(commentPageQueryDTO);

        long total = page.getTotal();
        List<CommentVO> records = page.getResult();

        return new PageResult(total,records);
    }


}
