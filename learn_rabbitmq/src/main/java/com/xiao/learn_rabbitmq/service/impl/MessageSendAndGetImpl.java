package com.xiao.learn_rabbitmq.service.impl;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.config.RabbitMQConfig;
import com.xiao.learn_rabbitmq.pojo.User;
import com.xiao.learn_rabbitmq.rabbitmq.RabbitMQController;
import com.xiao.learn_rabbitmq.service.MessageSendAndGet;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;


/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 11:06:55
 * @description
 */
@Service
public class MessageSendAndGetImpl implements MessageSendAndGet {

    @Resource
    private ConnectionFactory connectionFactory;
    private RabbitTemplate rabbitTemplate;

    public MessageSendAndGetImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void commodity(String message) {

        User user = new User(message, "test" + message);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY + "_a", user, correlationData);
    }


    @Override
    public void secKill(String message) {
        User user = new User(message, "test" + message);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        ReturnedMessage returnedMessage = new ReturnedMessage(new Message(user.toString().getBytes()), 0, null, null, null);
        correlationData.setReturned(returnedMessage);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY + "_b", user, correlationData);
    }

    @Override
    public void batchSend(String message) {
    }


}
