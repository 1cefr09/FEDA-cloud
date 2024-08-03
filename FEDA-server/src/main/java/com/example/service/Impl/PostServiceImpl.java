package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.context.BaseContext;
import com.example.dto.PostDTO;
import com.example.dto.PostPageQueryDTO;
import com.example.entity.Category;
import com.example.entity.Post;
import com.example.exception.AccountBannedException;
import com.example.exception.ContentIsEmptyException;
import com.example.exception.TitleIsEmptyException;
import com.example.mapper.PostMapper;
import com.example.mapper.UserMapper;
import com.example.result.PageResult;
import com.example.service.PostService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询
     * @param postPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(PostPageQueryDTO postPageQueryDTO) {
//
//        PageHelper.startPage(postPageQueryDTO.getPage(),postPageQueryDTO.getPageSize());
//        //下一条sql进行分页，自动加入limit关键字分页
//        Page<Category> page = postMapper.pageQuery(postPageQueryDTO);
//        return new PageResult(page.getTotal(),page.getResult());
        return null;
    }


    /**
     * 用户发帖
     * @param postDTO
     * @return
     */
    @Override
    public Post userPost(PostDTO postDTO) {
        System.out.println("传入Service层的DTO：" + postDTO);
        // 数据验证（例如，检查标题和内容是否为空）
        if (postDTO.getTitle() == null || postDTO.getTitle().isEmpty()) {
            throw new TitleIsEmptyException(MessageConstant.TITLE_EMPTY);
        }
        if (postDTO.getContent() == null || postDTO.getContent().isEmpty()) {
            throw new ContentIsEmptyException(MessageConstant.CONTENT_EMPTY);
        }
        if (userMapper.getIsBanned(postDTO.getAuthorId())) {
            throw new AccountBannedException(MessageConstant.ACCOUNT_BANNED);
        }

        Post post = new Post();
        BeanUtils.copyProperties(postDTO,post);
        post.setAuthorId(postDTO.getAuthorId());
//        System.out.println("发帖：" + post);
        // 保存实体到数据库
        postMapper.insert(post);

        // 返回保存后的实体
        return post;

    }

    @Override
    public void postDelete(Long id) {

    }
}
