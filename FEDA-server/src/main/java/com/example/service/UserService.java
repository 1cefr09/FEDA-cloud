package com.example.service;

import com.example.dto.UserDTO;
import com.example.dto.UserLoginDTO;
import com.example.entity.User;


public interface UserService {

    void UserRegister(UserDTO userDTO);

    User UserLogin(UserLoginDTO userLoginDTO);



}
