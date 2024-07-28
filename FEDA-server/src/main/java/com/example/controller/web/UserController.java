package com.example.controller.web;

import com.example.dto.UserDTO;
import com.example.result.Result;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 用户
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Result UserRegister(@RequestBody UserDTO userDTO){
        //判断两次密码是否一致
        if (!Objects.equals(userDTO.getPassword(), userDTO.getPasswordRepeat())){
            return Result.error("两次密码不一致");
        }

        log.info("用户注册");
        userService.UserRegister(userDTO);
        return Result.success();

    }


}
