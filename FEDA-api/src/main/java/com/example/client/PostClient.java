package com.example.client;

import com.example.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("post-service")
public interface PostClient {

    @PostMapping("/api/post/update")
    Result update( @RequestParam Long id);

    @PostMapping("/api/post/updateUsername")
    Result updateUsername(@RequestParam Long id, @RequestParam String username);

}
