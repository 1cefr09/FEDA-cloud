package com.example.listener;

import com.example.dto.AdminActionDTO;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserListener {

    private final UserService userService;

    @RabbitListener(queues = "user.ban.queue")
    public void  banUser(AdminActionDTO adminActionDTO){
        log.info("ban user: {}", adminActionDTO);
        userService.banUser(adminActionDTO);
    }

    @RabbitListener(queues = "user.unban.queue")
    public void unBanUser(Long id){
        log.info("unban user: {}", id);
        userService.unBanUser(id);
    }


}
