package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.dto.UserDTO;
import com.example.dto.UserLoginDTO;
import com.example.entity.User;
import com.example.exception.AccountBannedException;
import com.example.exception.AccountNotFoundException;
import com.example.exception.PasswordErrorException;
import com.example.exception.UsernameRepetitionException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void UserRegister(UserDTO userDTO){
        User user = new User();

        //检查是否有重复用户名
        if (userMapper.getByUsername(userDTO.getUsername()) != null){
            throw new UsernameRepetitionException(MessageConstant.USERNAME_EXIST);
        }

        BeanUtils.copyProperties(userDTO,user);
        String originalPassword = userDTO.getPassword();
        user.setPassword(DigestUtils.sha256Hex(originalPassword)); //保存加密后的密码
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

//        password = DigestUtils.md5DigestAsHex(password.getBytes());//苍穹外卖使用的MD5加密方法，此处使用的是commons-codec的SHA256加密方法
        password = DigestUtils.sha256Hex(password);
        if (!password.equals(user.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //此处检测账户是否被ban使用boolean判断，外卖中使用用户状态所处的不同数字来判断
//        if (user.isBanned()){
//            throw new AccountBannedException(MessageConstant.ACCOUNT_BANNED);
//
//        }
        return user;

    }

}
