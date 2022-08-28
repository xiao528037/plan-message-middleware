package com.xiao.learn_rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.pojo.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    public void executor(User msg, Channel channel, Message message) {
        try {
            System.out.println("queue_b :" + context.getEnvironment().getProperty("server.port") + "> value: " + msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {

        }

    }
}
