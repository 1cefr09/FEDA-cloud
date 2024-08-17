package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;

    private Long senderId;

    private String senderName;

    private Long receiverId;

    private String receiverName;

    private String content;

    private boolean haveRead = false;

    private LocalDateTime updateTime;
}
