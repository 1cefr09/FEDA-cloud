package com.example.mapper;

import com.example.entity.Message;
import com.example.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {
    /**
     *
     * @param message
     */
    void insert(Message message);
}
