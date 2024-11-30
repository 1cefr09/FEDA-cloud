package com.example.controller.web;

import com.example.constant.MessageConstant;
import com.example.dto.AdminActionDTO;
import com.example.dto.CategoryDTO;
import com.example.exception.TypeNotSameException;
import com.example.result.Result;
import com.example.service.AdminActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling admin actions.
 * 管理员操作控制器
 */
@RestController
@RequestMapping("/api/adminAction")
@Slf4j
public class AdminActionController {

    @Autowired
    private AdminActionService adminActionService;


    /**
     * Bans a user.
     * 禁用用户
     *
     * @param adminActionDTO the admin action data transfer object 管理员操作数据传输对象
     * @return the result of the operation 操作结果
     */
    @PostMapping("/banUser")
    public Result banUser(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        adminActionService.banUser(adminActionDTO);
        return Result.success();
    }

}