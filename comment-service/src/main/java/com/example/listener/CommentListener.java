package com.example.listener;

import com.example.entity.User;
import com.example.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentListener {

    private final CommentService commentService;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "comment.updateUsername.queue", durable = "true"),
            exchange = @Exchange(name = "comment.topic", type = ExchangeTypes.TOPIC),
            key = {"comment.updateUsername"}
    ))
    public void updateUsername(User user) {
        Long userId = user.getId();
        String username = user.getUsername();
        log.info("update username: {}", userId);
        commentService.updateUsername(userId, username);
    }

}
