package com.example.controller.web;

import com.example.annotation.CheckBanStatus;
import com.example.context.BaseContext;
import com.example.dto.MessageDTO;
import com.example.dto.MessagePageQueryDTO;
import com.example.dto.PostDTO;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getMessage")
    @ApiOperation(value = "获取与另一用户私信接口")
    Result<PageResult> getMessage(@RequestParam("receiverId") long receiverId , @RequestParam(required = false) Integer pageSize) {
        long senderId = BaseContext.getCurrentId();
        MessagePageQueryDTO messagePageQueryDTO = new MessagePageQueryDTO();
        messagePageQueryDTO.setSenderId(senderId);
        messagePageQueryDTO.setReceiverId(receiverId);
        if (pageSize != null) {
            messagePageQueryDTO.setPageSize(pageSize);
        }
        log.info("获取与另一用户私信{}", messagePageQueryDTO);

        PageResult messagePageResult = messageService.getMessage(messagePageQueryDTO);


        return Result.success(messagePageResult);
    }


}
