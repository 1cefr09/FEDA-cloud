package com.example.mapper;

import com.example.entity.Message;
import com.example.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MessageMapper {
    /**
     *
     * @param message
     */
    void insert(Message message);

    void updateUsername(@Param("Id") long Id, @Param("username") String username);
}
