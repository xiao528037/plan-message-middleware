package com.xiao.learn_rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 15:33:29
 * @description
 */


@Component
@RabbitListener(queues = "learn_queue_b")
public class Consumer2 implements Consumer {

    @Autowired
    private ApplicationContext context;

    @Override
    @RabbitHandler
    public void executor(String message) {
        System.out.println("queue_b :" + context.getEnvironment().getProperty("server.port") + "> value: " + message);
    }
}
