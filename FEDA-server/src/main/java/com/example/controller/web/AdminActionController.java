package com.example.controller.web;

import com.example.constant.MessageConstant;
import com.example.dto.AdminActionDTO;
import com.example.dto.CategoryDTO;
import com.example.exception.TypeNotSameException;
import com.example.result.Result;
import com.example.service.AdminActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * Promotes a user to admin.
     * 将用户提升为管理员
     *
     * @param adminActionDTO the admin action data transfer object 管理员操作数据传输对象
     * @return the result of the operation 操作结果
     */
    @PostMapping("/userToAdmin")
    public Result userToAdmin(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        adminActionService.userToAdmin(adminActionDTO);
        return Result.success();
    }

    /**
     * Bans a user, post, comment, or board based on the action type.
     * 根据操作类型禁用用户、帖子、评论或板块
     *
     * @param adminActionDTO the admin action data transfer object 管理员操作数据传输对象
     * @return the result of the operation 操作结果
     * @throws TypeNotSameException if the action type is not recognized 如果操作类型无法识别
     */
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
            case "banCategory":
                adminActionService.banCategory(adminActionDTO);
                break;
            default:
                throw new TypeNotSameException(MessageConstant.TYPE_NOT_SAME);
        }
        return Result.success();
    }

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

    /**
     * Bans a post.
     * 禁用帖子
     *
     * @param adminActionDTO the admin action data transfer object 管理员操作数据传输对象
     * @return the result of the operation 操作结果
     */
    @PostMapping("/banPost")
    public Result banPost(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        adminActionService.banPost(adminActionDTO);
        return Result.success();
    }

    /**
     * Bans a comment.
     * 禁用评论
     *
     * @param adminActionDTO the admin action data transfer object 管理员操作数据传输对象
     * @return the result of the operation 操作结果
     */
    @PostMapping("/banComment")
    public Result banComment(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        adminActionService.banComment(adminActionDTO);
        return Result.success();
    }

    /**
     * Creates a new board.
     * 创建新板块
     *
     * @param categoryDTO the board data transfer object 板块数据传输对象
     * @return the result of the operation 操作结果
     */
    @PostMapping("/createCategory")
    public Result createCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("categoryDTO: {}", categoryDTO);
        adminActionService.createCategory(categoryDTO);
        return Result.success();
    }

    /**
     * Bans a board.
     * 禁用板块
     *
     * @param adminActionDTO the admin action data transfer object 管理员操作数据传输对象
     * @return the result of the operation 操作结果
     */
    @PostMapping("/banCategory")
    public Result banCategory(@RequestBody AdminActionDTO adminActionDTO) {
        log.info("adminActionDTO: {}", adminActionDTO);
        adminActionService.banCategory(adminActionDTO);
        return Result.success();
    }
}