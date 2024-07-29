package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.dto.UserDTO;
import com.example.dto.UserLoginDTO;
import com.example.entity.User;
import com.example.exception.AccountBannedException;
import com.example.exception.AccountNotFoundException;
import com.example.exception.PasswordErrorException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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

    @Override
    //完善用户登录方法
    public User UserLogin(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);

        if (user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //此处检测账户是否被ban使用boolean判断，外卖中使用用户状态所处的不同数字来判断
        if (user.isBanned()){
            throw new AccountBannedException(MessageConstant.ACCOUNT_BANNED);

        }
        return user;

    }

}
