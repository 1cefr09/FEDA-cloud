package com.example.mapper;

import com.example.dto.MessagePageQueryDTO;
import com.example.entity.Message;
import com.example.entity.Post;
import com.example.vo.MessageVO;
import com.github.pagehelper.Page;
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

    Page<MessageVO> getMessage(MessagePageQueryDTO messagePageQueryDTO);
}
