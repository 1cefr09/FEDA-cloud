package com.example.service.Impl;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void UserRegister(UserDTO userDTO){
        User user = new User();

        BeanUtils.copyProperties(userDTO,user);
        userMapper.insert(user);

    }

}
