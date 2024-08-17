package com.example.service;

import com.example.dto.MessageDTO;
import com.example.entity.Message;

public interface MessageService {

    /**
     * 用户发私信
     * @param messageDTO
     * @return
     */
    Message sendMessage(MessageDTO messageDTO);
}
