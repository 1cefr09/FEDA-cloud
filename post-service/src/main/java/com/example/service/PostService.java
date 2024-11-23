package com.example.service;

import com.example.dto.PostDTO;
import com.example.dto.PostPageQueryDTO;
import com.example.entity.Post;
import com.example.result.PageResult;
import com.example.vo.PostVO;

public interface PostService {

    /**
     * post分页查询
     * @param postPageQueryDTO
     * @return
     */
    PageResult postPageQuery(PostPageQueryDTO postPageQueryDTO);

    /**
     * 用户发帖
     * @param postDTO
     * @return
     */
    Post userPost(PostDTO postDTO);

    long getCategoryIdByName(String categoryName);

    PostVO getPostById(Long id);

    void update(long Id);

    void updateUsername(Long Id, String username);

}
