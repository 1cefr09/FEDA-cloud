package com.example.client;

import com.example.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("comment-service")
public interface CommentClient {

    @PostMapping("/api/comment/updateUsername")
    void updateUsername(@RequestParam Long id, @RequestParam String username);
}
