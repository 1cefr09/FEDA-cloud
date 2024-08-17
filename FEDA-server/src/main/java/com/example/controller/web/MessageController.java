package com.example.controller.web;

import com.example.annotation.CheckBanStatus;
import com.example.context.BaseContext;
import com.example.dto.MessageDTO;
import com.example.dto.PostDTO;
import com.example.result.Result;
import com.example.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
@Slf4j
@Api(tags = "私信相关接口")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @PostMapping("/sendMessage")
    @ApiOperation(value = "发私信接口")
    @CheckBanStatus
        //此处拦截检测是否被ban
    Result sendMessage(@RequestBody MessageDTO messageDTO) {
        long senderId = BaseContext.getCurrentId();
        messageDTO.setSenderId(senderId);
        log.info("发私信{}", messageDTO);
        messageService.sendMessage(messageDTO);
        return Result.success();
    }

}
