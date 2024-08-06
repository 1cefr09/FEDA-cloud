package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminAction {
    private Long id;

    private Long adminId;

    private String actionType;


    private Long targetId;

    private LocalDateTime timestamp;
}
