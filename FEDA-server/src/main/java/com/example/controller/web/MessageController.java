package com.example.controller.web;

import com.example.annotation.CheckBanStatus;
import com.example.context.BaseContext;
import com.example.dto.MessageDTO;
import com.example.dto.MessagePageQueryDTO;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling private message-related actions.
 * 私信相关操作控制器
 */
@RestController
@RequestMapping("/api/message")
@Slf4j
@Api(tags = "私信相关接口")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Sends a private message.
     * 发私信
     *
     * @param messageDTO the message data transfer object 私信数据传输对象
     * @return the result of the operation 操作结果
     */
    @PostMapping("/sendMessage")
    @ApiOperation(value = "发私信接口")
    @CheckBanStatus
    public Result sendMessage(@RequestBody MessageDTO messageDTO) {
        long senderId = BaseContext.getCurrentId();
        messageDTO.setSenderId(senderId);
        log.info("发私信{}", messageDTO);
        messageService.sendMessage(messageDTO);
        return Result.success();
    }

    /**
     * Retrieves private messages with another user.
     * 获取与另一用户的私信
     *
     * @param receiverId the ID of the receiver 接收者的ID
     * @param pageSize the size of the page 页大小
     * @return the result containing the paginated list of messages 包含分页私信列表的结果
     */
    @GetMapping("/getMessage")
    @ApiOperation(value = "获取与另一用户私信接口")
    public Result<PageResult> getMessage(@RequestParam("receiverId") long receiverId, @RequestParam(required = false) Integer pageSize) {
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