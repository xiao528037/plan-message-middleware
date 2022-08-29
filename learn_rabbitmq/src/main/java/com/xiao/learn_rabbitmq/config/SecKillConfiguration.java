package com.xiao.learn_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-29 14:01:15
 * @description
 */
@Configuration
public class SecKillConfiguration {

    public static final String QUEUE_NAME = "learn_queue";

    public static final String EXCHANGE_NAME = "learn_exchange";

    public static final String ROUTING_KEY = "learn_queue_one";


    @Bean
    public Queue queueCommodity() {
        return new Queue(QUEUE_NAME + "_a", true, false, false);
    }

    @Bean
    public Queue queueSecKill() {
        return new Queue(QUEUE_NAME + "_b", true, false, false);
    }

    @Bean
    public DirectExchange exchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).build();
    }

    @Bean
    public Binding bindingCommodity() {
        return BindingBuilder.bind(queueCommodity()).to(exchange()).with(ROUTING_KEY + "_a");
    }

    @Bean
    Binding bindingSecKill() {
        return BindingBuilder.bind(queueSecKill()).to(exchange()).with(ROUTING_KEY + "_b");
    }
}

