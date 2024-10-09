package com.example.service;

import com.example.dto.MessageDTO;
import com.example.dto.MessagePageQueryDTO;
import com.example.entity.Message;
import com.example.result.PageResult;

public interface MessageService {

    /**
     * 用户发私信
     * @param messageDTO
     * @return
     */
    Message sendMessage(MessageDTO messageDTO);

    /**
     * 用户查看私信
     * @param messagePageQueryDTO
     * @return
     */
    PageResult getMessage(MessagePageQueryDTO messagePageQueryDTO);


}
