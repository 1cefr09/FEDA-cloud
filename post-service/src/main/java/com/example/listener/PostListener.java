package com.example.listener;

import com.example.service.PostService;
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

}
