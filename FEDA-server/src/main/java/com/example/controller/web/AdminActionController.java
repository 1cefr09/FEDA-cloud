package com.example.controller.web;

import com.example.constant.MessageConstant;
import com.example.dto.AdminActionDTO;
import com.example.exception.TypeNotSameException;
import com.example.result.Result;
import com.example.service.AdminActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adminAction")
@Slf4j
public class AdminActionController {

    @Autowired
    private AdminActionService adminActionService;

    @PostMapping("/userToAdmin")
    public Result userToAdmin(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        adminActionService.userToAdmin(adminActionDTO);
        return Result.success();
    }

    @PostMapping("/ban")
    public Result ban(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        switch (adminActionDTO.getActionType()) {
            case "banUser":
                adminActionService.banUser(adminActionDTO);
                break;
            case "banPost":
                adminActionService.banPost(adminActionDTO);
                break;
            case "banComment":
                adminActionService.banComment(adminActionDTO);
                break;
            default:
                throw new TypeNotSameException(MessageConstant.TYPE_NOT_SAME);
        }
        return Result.success();
    }


    @PostMapping("/banUser")
    public Result banUser(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        adminActionService.banUser(adminActionDTO);
        return Result.success();
    }

    @PostMapping("/banPost")
    public Result banPost(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        adminActionService.banPost(adminActionDTO);
        return Result.success();
    }

    @PostMapping("/banComment")
    public Result banComment(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        adminActionService.banComment(adminActionDTO);
        return Result.success();
    }

}