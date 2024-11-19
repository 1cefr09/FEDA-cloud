package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVO implements Serializable {

    private Long id;

    private String categoryName;

    private boolean isBanned = false;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
