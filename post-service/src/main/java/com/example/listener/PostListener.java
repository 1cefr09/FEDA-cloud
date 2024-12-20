package com.example.listener;

import com.example.entity.User;
import com.example.service.PostService;
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
public class PostListener {

    private final PostService postService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "post.update.queue", durable = "true"),
            exchange = @Exchange(name = "post.topic", type = ExchangeTypes.TOPIC),
            key = {"post.update"}
    ))
    public void updatePost(Long postId) {
        log.info("update post: {}", postId);
        postService.update(postId);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "post.updateUsername.queue", durable = "true"),
            exchange = @Exchange(name = "post.topic", type = ExchangeTypes.TOPIC),
            key = {"post.updateUsername"}
    ))
    public void updateUsername(User user) {
        Long userId = user.getId();
        String username = user.getUsername();
        log.info("update username: {}", userId);
        postService.updateUsername(userId, username);
    }

}
