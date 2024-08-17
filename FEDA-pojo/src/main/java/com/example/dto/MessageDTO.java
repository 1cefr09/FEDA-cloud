package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageDTO implements Serializable {
    private Long senderId;

    private String receiverName;

    private String content;

}
