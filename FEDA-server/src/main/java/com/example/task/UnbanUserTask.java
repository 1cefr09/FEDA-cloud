package com.example.task;

import com.example.mapper.UserMapper;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class UnbanUserTask {

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 * * * ?") // 每小时执行一次
//    @Scheduled(cron = "0 * * * * ?")//每分钟执行一次
    public void unbanUsers() {
        userService.unBanUsers();
        log.info("解封用户任务执行");
    }
}