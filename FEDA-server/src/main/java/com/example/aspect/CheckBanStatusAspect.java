package com.example.aspect;

import com.example.constant.JwtClaimsConstant;
import com.example.constant.MessageConstant;
import com.example.context.BaseContext;
import com.example.entity.User;
import com.example.exception.AccountBannedException;
import com.example.mapper.UserMapper;
import com.example.properties.JwtProperties;
import com.example.service.PostService;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义切面实现用户禁用检测
 */
@Aspect
@Component
@Slf4j
public class CheckBanStatusAspect {
    @Autowired

    private JwtProperties jwtProperties;
    @Autowired

    private UserMapper userMapper;
    @Autowired
    HttpServletRequest request;

    @Before("@annotation(com.example.annotation.CheckBanStatus)")
    public void checkBanStatus() throws Exception{
        //从当前线程中获得UserId
        //从请求头中获取token
        //String token = request.getHeader(jwtProperties.getAdminTokenName());
        //Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        if (!user.isActivated()){
            throw new AccountBannedException(MessageConstant.ACCOUNT_NOT_ACTIVATED);
        }

        if (user.isBanned()){
            throw new AccountBannedException(MessageConstant.ACCOUNT_BANNED);
        }
    }

}
