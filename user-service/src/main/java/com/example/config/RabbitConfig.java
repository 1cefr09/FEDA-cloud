package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue userBanQueue() {
        return new Queue("user.ban.queue", true);
    }

    @Bean
    public Queue userUnbanQueue() {
        return new Queue("user.unban.queue", true);
    }

    @Bean
    public DirectExchange userExchange() {
        return ExchangeBuilder.directExchange("user.direct").durable(true).build();
    }

    @Bean
    public DirectExchange delayExchange() {
        return ExchangeBuilder.directExchange("user.delay.direct").delayed().durable(true).build();
    }

    @Bean
    public Binding userBanBinding(){
        return BindingBuilder.bind(userBanQueue()).to(userExchange()).with("user.ban");
    }

    @Bean
    public Binding userUnbanBinding(){
        return BindingBuilder.bind(userUnbanQueue()).to(userExchange()).with("user.unban");
    }

    @Bean
    public Binding userDelayBinding(){
        return BindingBuilder.bind(userUnbanQueue()).to(delayExchange()).with("user.delay.unban");
    }


}
