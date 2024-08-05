package com.example.interceptor;

import com.example.annotation.CheckBanStatus;
import com.example.constant.JwtClaimsConstant;
import com.example.constant.MessageConstant;
import com.example.exception.AccountBannedException;
import com.example.properties.JwtProperties;
import com.example.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户是否被ban的拦截器
 */
@Component
@Slf4j
public class UserIsBannedInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        //从请求头中取出令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());
        Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
        Boolean isBanned = Boolean.valueOf(claims.get(JwtClaimsConstant.IS_BANNED).toString());

        //校验是否被ban
        log.info("校验账户是否被禁用");
        //检查当前的方法是否标注了CheckBanStatus
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CheckBanStatus checkBanStatus = handlerMethod.getMethodAnnotation(CheckBanStatus.class);
        if (checkBanStatus != null && isBanned){
            log.info("账户被禁用");
            throw new AccountBannedException(MessageConstant.ACCOUNT_BANNED);
        }
        return true;

    }

}
