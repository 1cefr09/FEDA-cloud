package com.example.service;

import com.example.dto.PostDTO;
import com.example.dto.PostPageQueryDTO;
import com.example.entity.Post;
import com.example.result.PageResult;
import com.example.result.Result;

public interface PostService {

    /**
     * 分页查询
     * @param postPageQueryDTO
     * @return
     */
    PageResult pageQuery(PostPageQueryDTO postPageQueryDTO);

    /**
     * 用户发帖
     * @param postDTO
     * @return
     */
    Post userPost(PostDTO postDTO);

    /**
     *根据帖子id删帖
     * @param id
     */
    void postDelete(Long id);


}
