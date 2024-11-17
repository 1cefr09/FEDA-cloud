package com.example.mapper;

import com.example.dto.PostPageQueryDTO;
import com.example.entity.Post;
import com.example.vo.PostVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
    /**
     * 插入数据
     * @param post
     */
    void insert(Post post);


    void update(long Id);

    void updateUsername(@Param("Id") long Id, @Param("username") String username);

    Boolean getIsBanned(long id);

    void updatePostBanned(@Param("targetId") long targetId,@Param("postIsBanned") boolean postIsBanned);

    /**
     * post分页查询
     * @param postPageQueryDTO
     * @return
     */
    Page<PostVO> postPageQuery(PostPageQueryDTO postPageQueryDTO);

    PostVO getPostById(Long id);

    long getAuthorIdByPostId(long postId);
}
