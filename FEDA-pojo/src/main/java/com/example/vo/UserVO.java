package com.example.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 未完成
 */
@Builder
@Data
public class UserVO implements Serializable {
    private Long id;

    private String username;

//    private String password;

    private boolean isActivated;

    private String role;

    private String email;

    private boolean isBanned;

    private LocalDateTime unbanTime = null;

}
