package com.example.config;

import com.example.interceptor.JwtTokenAdminInterceptor;
import com.example.interceptor.UserIsBannedInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    @Autowired
    private UserIsBannedInterceptor userIsBannedInterceptor;


    //拦截器按顺序注册并且执行
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/api/**")             //设置拦截路径
                .excludePathPatterns("/api/user/login","/api/user/register");          //设置放行路径

        //用户是否被ban拦截器
        registry.addInterceptor(userIsBannedInterceptor)
                .addPathPatterns("/api/post/userPost");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {//跨域配置
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081") // 允许的源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true); // 允许发送凭证
    }


}
