package com.example.client;

import com.example.result.Result;
import com.example.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserClient {

    @GetMapping("/api/user/getSelfInfo")
    Result<UserVO> getSelfInfo();

    @GetMapping("/api/user/getUserInfo")
    Result<UserVO> getUserInfo(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String username
    );


}
