package com.example.filter;

import com.example.constant.JwtClaimsConstant;
import com.example.properties.AuthProperties;
import com.example.properties.JwtProperties;
import com.example.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthGlobalFilter implements GlobalFilter , Ordered {

    private final JwtProperties jwtProperties;
    private final AuthProperties authProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取request
        ServerHttpRequest request = exchange.getRequest();
        //是否需要拦截
        if(isExclude(request.getPath().toString())){
            return chain.filter(exchange);
        }
        //从请求头中获取令牌
        List<String> headers = request.getHeaders().get(jwtProperties.getAdminTokenName());
        if(headers == null || headers.isEmpty()){
            //不通过响应401
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String token = headers.get(0);
        Long userId = null;
        //进行令牌的校验
        try {
            log.info("jwt校验{}",token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
            userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户id{}",userId);
        }catch (Exception e){
            //不通过响应401
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //将用户id传递给后端
        String userInfo = userId.toString();
        exchange.mutate().request(request.mutate().header("userInfo", userInfo).build()).build();
        return chain.filter(exchange);

    }

    private boolean isExclude(String path) {
        for(String excludePath : authProperties.getExcludePaths()){
            if(antPathMatcher.match(excludePath,path)){
                return true;
            }
        }
        return false;
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
