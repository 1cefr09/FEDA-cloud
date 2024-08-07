package com.example.mapper;

import com.example.dto.PostPageQueryDTO;
import com.example.entity.Category;
import com.example.entity.Post;
import com.example.entity.User;
import com.example.vo.PostVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PostMapper {
    /**
     * 插入数据
     * @param post
     */
    void insert(Post post);


    void update(long Id);

    void updateUsername(@Param("Id") long Id, @Param("username") String username);

    /**
     * post分页查询
     * @param postPageQueryDTO
     * @return
     */
    Page<PostVO> postPageQuery(PostPageQueryDTO postPageQueryDTO);
}
