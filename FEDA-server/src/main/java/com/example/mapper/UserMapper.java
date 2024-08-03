package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 插入数据
     * @param user
     */
    void insert(User user);
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    /**
     * 查询账户是否被ban
     * @param id
     * @return
     */
    Boolean getIsBanned(long id);

}
