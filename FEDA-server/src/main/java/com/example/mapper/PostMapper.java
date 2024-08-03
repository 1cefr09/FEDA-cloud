package com.example.mapper;

import com.example.dto.PostPageQueryDTO;
import com.example.entity.Category;
import com.example.entity.Post;
import com.example.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PostMapper {
    /**
     * 插入数据
     * @param post
     */
    void insert(Post post);


    /**
     * 查询账户是否被ban
     * @param is_banned
     * @return
     */
    @Select("select * from post where author_id = #{authorId}")
    Boolean getIsBanned(Boolean is_banned);


    /**
     * 分页查询
     * @param postPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(PostPageQueryDTO postPageQueryDTO);
}
