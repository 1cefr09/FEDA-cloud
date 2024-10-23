package com.example.controller.web;

import com.example.constant.JwtClaimsConstant;
import com.example.constant.MessageConstant;
import com.example.context.BaseContext;
import com.example.dto.UserDTO;
import com.example.dto.UserLoginDTO;
import com.example.entity.User;
import com.example.exception.ParamErrorException;
import com.example.mapper.UserMapper;
import com.example.properties.JwtProperties;
import com.example.result.Result;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.vo.UserLoginVO;
import com.example.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for handling user-related actions.
 * 用户相关操作控制器
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * Registers a new user.
     * 用户注册
     *
     * @param userDTO the user data transfer object 用户数据传输对象
     * @return the result of the operation 操作结果
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Result UserRegister(@RequestBody UserDTO userDTO){
        log.info("用户注册{}", userDTO);
        userService.UserRegister(userDTO);
        return Result.success();
    }

    /**
     * Logs in a user.
     * 用户登录
     *
     * @param userLoginDTO the user login data transfer object 用户登录数据传输对象
     * @return the result containing the user login view object 包含用户登录视图对象的结果
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public Result<UserLoginVO> UserLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        User user = userService.UserLogin(userLoginDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .isBanned(userMapper.getIsBanned(user.getId()))
                .isActivated(userMapper.getIsActivated(user.getId()))
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

    /**
     * Sends an activation email.
     * 发送激活邮件
     *
     * @return the result of the operation 操作结果
     */
    @GetMapping("/sendActivate")
    @ApiOperation(value = "发送邮件激活地址")
    public Result sendActivateEmail() {
        Long userId = BaseContext.getCurrentId();
        userService.sendActivateEmail(userId);
        return Result.success(MessageConstant.MAIL_SEND_SUCCESS);
    }

    /**
     * Activates a user.
     * 用户激活
     *
     * @param code the activation code 激活码
     * @param Id the user ID 用户ID
     * @return the result of the operation 操作结果
     */
    @GetMapping("/activate")
    @ApiOperation(value = "用户激活")
    public Result activateUser(@RequestParam String code, @RequestParam(required = false) Long Id) {
        log.info("用户激活，code:{}", code);
        if (Id == null){
            Id = BaseContext.getCurrentId();
        }
        userService.activateUser(Id, code);
        return Result.success(MessageConstant.ACTIVATE_SUCCESS);
    }

    /**
     * Retrieves the current user's information.
     * 获取当前用户信息
     *
     * @return the result containing the user view object 包含用��视图对象的结果
     */
    @GetMapping("/getSelfInfo")
    @ApiOperation("获取本用户信息")
    public Result<UserVO> getSelfInfo(){
        Long userId = BaseContext.getCurrentId();
        log.info("获取本用户信息，userId:{}",userId);
        User user = userService.getUserById(userId);
        UserVO userVO = UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .email(user.getEmail())
                .isBanned(user.isBanned())
                .isActivated(user.isActivated())
                .unbanTime(user.getUnbanTime())
                .build();
        return Result.success(userVO);
    }

    /**
     * Retrieves information of another user.
     * 获取其他用户信息
     *
     * @param userId the user ID 用户ID
     * @param username the username 用户名
     * @return the result containing the user view object 包含用户视图对象的结果
     */
    @GetMapping("/getUserInfo")
    @ApiOperation("获取其它用户信息")
    public Result<UserVO> getUserInfo(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String username){
        log.info("获取用户信息，userId:{},username:{}",userId,username);
        User user;
        if (userId != null){
            user = userService.getUserById(userId);}
        else if (username != null){
            user = userService.getUserByUsername(username);
        }
        else {
            throw new ParamErrorException("参数错误");
        }
        UserVO userVO = UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .isBanned(user.isBanned())
                .build();
        return Result.success(userVO);
    }

    /**
     * Updates the current user's information.
     * 更新当前用户信息
     *
     * @param userDTO the user data transfer object 用户数据传输对象
     * @return the result containing the updated user view object 包含更新后用户视图对象的结果
     */
    @PostMapping("/updateUser")
    @ApiOperation("更新用户信息")
    public Result<UserVO> updateUser(@RequestBody UserDTO userDTO){
        Long userId = BaseContext.getCurrentId();
        log.info("更新用户信息，userId:{},userDTO:{}",userId,userDTO);
        UserVO userVO = userService.updateUser(userId,userDTO);
        return Result.success(userVO);
    }
}