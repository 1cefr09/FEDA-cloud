package com.example.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MessageVO implements Serializable {
    private Long id;

    private Long senderId;

    private Long receiverId;

    private String receiverName;

    private String content;

    private LocalDateTime createTime;
}
