package com.example.service.Impl;

import com.example.client.CategoryClient;
import com.example.client.UserClient;
import com.example.constant.MessageConstant;
import com.example.dto.PostDTO;
import com.example.dto.PostPageQueryDTO;
import com.example.entity.Post;
import com.example.exception.ContentIsEmptyException;
import com.example.exception.TitleIsEmptyException;
import com.example.mapper.PostMapper;
import com.example.result.PageResult;
import com.example.service.PostService;
import com.example.utils.InfoIsValidUtil;
import com.example.vo.CategoryVO;
import com.example.vo.PostVO;
import com.example.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
//这是一种线程池的使用方法，，另一种是直接使用@Async注解，在commentServiceImpl中有使用
    private final PostMapper postMapper;

    private final UserClient userClient;
    private final CategoryClient categoryClient;

    @Autowired
    public PostServiceImpl(PostMapper postMapper, UserClient userClient, CategoryClient categoryClient) {
        this.postMapper = postMapper;
        this.userClient = userClient;
        this.categoryClient = categoryClient;
    }

    /**
     * post分页查询
     * @param postPageQueryDTO
     * @return
     */
    @Override
    public PageResult postPageQuery(PostPageQueryDTO postPageQueryDTO) {


        PageHelper.startPage(postPageQueryDTO.getPage(),postPageQueryDTO.getPageSize());

        Page<PostVO> page = postMapper.postPageQuery(postPageQueryDTO);


        long total = page.getTotal();
        List<PostVO> records = page.getResult();

        return new PageResult(total,records);

    }


    /**
     * 用户发帖
     * @param postDTO
     * @return
     */
    @Override
    public Post userPost(PostDTO postDTO) {
        System.out.println("发帖传入Service层的DTO：" + postDTO);
        // 数据验证（例如，检查标题和内容是否为空）
        if (postDTO.getTitle() == null || postDTO.getTitle().isEmpty()) {
            throw new TitleIsEmptyException(MessageConstant.TITLE_EMPTY);
        }
        if (postDTO.getContent() == null || postDTO.getContent().isEmpty()) {
            throw new ContentIsEmptyException(MessageConstant.CONTENT_EMPTY);
        }

        InfoIsValidUtil.isValidTitleOrKeywords(postDTO.getTitle());
        Post post = new Post();
        BeanUtils.copyProperties(postDTO,post);

        UserVO userVO = userClient.getUserInfo(postDTO.getAuthorId(), null).getData();
//        String authorName = userMapper.getUsernameById(post.getAuthorId());
        String authorName = userVO.getUsername();

        post.setAuthorName(authorName);
//        System.out.println("发帖：" + post);
        // 保存实体到数据库
        postMapper.insert(post);
//        taskExecutor.submit(() -> {
//            try {
//                postMapper.insert(post);
////                log.info("Post saved asynchronously: {}", post);
//            } catch (Exception e) {
//                log.error("Error saving post asynchronously", e);
//            }
//        });

        // 返回保存后的实体
        return post;

    }

    /**
     * 根据id获取帖子
     * @param id
     * @return
     */
    @Override
    public PostVO getPostById(Long id) {
        return postMapper.getPostById(id);
    }

    @Override
    public long getCategoryIdByName(String categoryName) {
        CategoryVO categoryVO = categoryClient.getCategoryByName(categoryName).getData();
        return categoryVO.getId();
    }

    @Override
    public void update(long Id) {
        postMapper.update(Id);
    }

    @Override
    public void updateUsername(Long Id, String username) {
        postMapper.updateUsername(Id, username);
    }
}
