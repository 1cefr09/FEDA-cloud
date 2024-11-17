package com.example.interceptor;

import com.example.constant.JwtClaimsConstant;
import com.example.context.BaseContext;
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
 * 设置JWT令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
       //获取请求头中的令牌
        String userId = request.getHeader("userInfo");

        if(userId == null|| userId.isEmpty()){
            log.info("用户未登录");
            response.setStatus(401);
            return false;
        }
        BaseContext.setCurrentId(Long.valueOf(userId));
        return true;
    }
}
