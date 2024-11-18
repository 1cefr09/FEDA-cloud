package com.example.dto;

import lombok.Data;

@Data
public class MessagePageQueryDTO {
    private Long receiverId;

    private Long senderId;

    private int page = 1;

    private int pageSize = 30;
}
