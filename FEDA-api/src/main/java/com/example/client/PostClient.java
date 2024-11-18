package com.example.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("post-service")
public interface PostClient {

    //TODO: 写openfeign的RPC接口


}
