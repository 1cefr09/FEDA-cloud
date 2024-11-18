package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private String Username;

    private String password;

//    private String passwordRepeat;

    private String email;
}
