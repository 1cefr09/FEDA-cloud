package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    //@Select("select * from user where username = #{username}")
    User getByUsername(String username);


    User getById(Long id);

    /**
     * 根据用户id查用户名
     * @param id
     * @return
     */
    //@Select("select username from user where id = #{id}")
    String getUsernameById(Long id);

    /**
     * 查询账户是否被ban
     * @param id
     * @return
     */
    Boolean getIsBanned(long id);

    void updateUserBanned(@Param("targetId") long targetId,@Param("userIsBanned") boolean userIsBanned);

    void update(User user);

    /**
     * 根据id查身份
     * @param id
     * @return
     */
    String getUserRoleById(Long id);

    void updateUserRole(@Param("targetId") long targetId,@Param("userRole") String userRole);

    void activateUser(@Param("id") Long id);
}
