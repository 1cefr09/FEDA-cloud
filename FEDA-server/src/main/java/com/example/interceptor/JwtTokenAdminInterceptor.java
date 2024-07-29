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
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置JWT令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     *校验JWT
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        System.out.println("当前线程的ID" + Thread.currentThread().getId());

        //如果拦截到的不是动态方法则直接放行
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        //从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //进行令牌的校验
        try {
            log.info("jwt校验“{}",token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户id",userId);
            BaseContext.setCurrentId(userId);
            //通过则放行
            return true;
        }catch (Exception e){
            //不通过响应401
            response.setStatus(401);
            return false;
        }
    }
}
