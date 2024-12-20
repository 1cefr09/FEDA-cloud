package com.example.service;

import com.example.dto.AdminActionDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserLoginDTO;
import com.example.entity.User;
import com.example.vo.UserVO;

import java.util.Date;


public interface UserService {

    void UserRegister(UserDTO userDTO);

    User UserLogin(UserLoginDTO userLoginDTO);

    User getUserById(Long id);

    User getUserByUsername(String username);

    void sendActivateEmail(Long id);

    void activateUser(Long Id, String code);

    UserVO updateUser(long id ,UserDTO userDTO);

    void unBanUsers();

    void unBanUser(long id);

    void banUser(AdminActionDTO adminActionDTO);

}
