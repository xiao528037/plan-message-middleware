package com.xiao.learn_rabbitmq.service.impl;

import com.xiao.learn_rabbitmq.config.*;
import com.xiao.learn_rabbitmq.pojo.User;
import com.xiao.learn_rabbitmq.service.MessageSendAndGet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;


/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 11:06:55
 * @description
 */
@Service
@Slf4j(topic = "messageSendAndGetImpl")
public class MessageSendAndGetImpl implements MessageSendAndGet {

    @Resource
    private ConnectionFactory connectionFactory;
    private RabbitTemplate rabbitTemplate;
    private Random random = new Random();

    public MessageSendAndGetImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void commodity(String message) {

        User user = new User(message, "test" + message);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(SecKillConfiguration.EXCHANGE_NAME, SecKillConfiguration.ROUTING_KEY + "_a", user, correlationData);
    }


    @Override
    public void secKill(String message) {
        User user = new User(message, "test" + message);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        ReturnedMessage returnedMessage = new ReturnedMessage(new Message(user.toString().getBytes()), 0, null, null, null);
        correlationData.setReturned(returnedMessage);
        rabbitTemplate.convertAndSend(SecKillConfiguration.EXCHANGE_NAME, SecKillConfiguration.ROUTING_KEY + "_b", user, correlationData);
    }

    @Override
    public void sendAllUser(User user) {
        log.info("本次发送的消息 {} ", user);
        rabbitTemplate.convertAndSend(RabbitMQConfigFanout.EXCHANGE_FANOUT, RabbitMQConfigFanout.ROUTING_KEY, user, new CorrelationData(UUID.randomUUID().toString()));
    }

    @Override
    public void directSend(User user, Integer sendType) {
        log.info("本次发送的消息 {} ", user);
        switch (sendType) {
            case 1:
                rabbitTemplate.convertAndSend(RabbitMQConfigDirect.EXCHANGE_DIRECT, RabbitMQConfigDirect.ROUTING_KEY_ONE, user);
                break;
            case 2:
                rabbitTemplate.convertAndSend(RabbitMQConfigDirect.EXCHANGE_DIRECT, RabbitMQConfigDirect.ROUTING_KEY_TWO, user);
                break;
            default:
                rabbitTemplate.convertAndSend(RabbitMQConfigDirect.EXCHANGE_DIRECT, RabbitMQConfigDirect.ROUTING_KEY_THREE, user);
                break;
        }

    }

    @Override
    public void topicSend(User user, String topic) {
        log.info("本次发送的消息 {} ", user);
        rabbitTemplate.convertAndSend(RabbitMQConfigTopic.EXCHANGE_TOPIC, topic, user, new CorrelationData(UUID.randomUUID().toString()));
    }

    @Override
    public void deadSend(User user) {
        log.info("本次发送的消息 {} ", user);

        rabbitTemplate.convertAndSend(RabbitMQConfigDead.EXCHANGE_ORDINARY, RabbitMQConfigDead.ROUTING_ORDINARY_KEY, user, message -> {
            //配置超时时间进入死信队列
            message.getMessageProperties().setExpiration("5000");
            message.getMessageProperties().setContentEncoding("UTF-8");
            return message;
        }, new CorrelationData(UUID.randomUUID().toString()));
    }

    @Override
    public void waitConsumer(User user) {
        String id = user.getId();
        if (Integer.parseInt(id) % 2 == 0) {
            rabbitTemplate.convertAndSend(RabbitMQConfigDelay.ORDINARY_EXCHANGE, RabbitMQConfigDelay.WAIT_TEN_SECOND_KEY, user, new CorrelationData(UUID.randomUUID().toString()));
        } else {
            rabbitTemplate.convertAndSend(RabbitMQConfigDelay.ORDINARY_EXCHANGE, RabbitMQConfigDelay.WAIT_TWENTY_SECOND_KEY, user, new CorrelationData(UUID.randomUUID().toString()));
        }
    }

    @Override
    public void customizeSecond(User user, int waitTime) {
        log.info("发送到消息是 {} 延迟消费时间 {}", user, waitTime);
        rabbitTemplate.convertAndSend(RabbitMQConfigDelay.ORDINARY_EXCHANGE, RabbitMQConfigDelay.WAIT_CUSTOMIZE_SECOND_KEY, user, message -> {
            message.getMessageProperties().setExpiration(Integer.toString(waitTime * 1000));
            return message;
        }, new CorrelationData(user.getUsername()));
    }

    @Override
    public void pluginConsumer(User user, int waitTime) {
        log.info("发送到消息是 {} 延迟消费时间 {}", user, waitTime);
        CorrelationData correlationData = new CorrelationData(user.getUsername());

        rabbitTemplate.convertAndSend(RabbitMQConfigPluginDelay.PLUGIN_WAIT_EXCHANGE, RabbitMQConfigPluginDelay.PLUGIN_WAIT_ROUTING_KEY
                , user, message -> {
                    message.getMessageProperties().setHeader("x-delay", waitTime * 1000);
                    return message;
                }, new CorrelationData(user.getUsername()));
    }

    @Override
    public void confirmMessage(User user) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMQConfigConfirm.CONFIRM_EXCHANGE, RabbitMQConfigConfirm.CONFIRM_QUEUE_KEY + "123", user, message1 -> {
            message1.getMessageProperties().setHeader("contentType", "application/json");
            return message1;
        }, correlationData);
    }

    @Override
    public void backupExchangeMessage(User user) {
        String id = user.getId();
        if (Integer.parseInt(id) % 2 == 0) {
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(RabbitMQConfigBackupExchange.BACKUP_ORDINARY_EXCHANGE, RabbitMQConfigBackupExchange.BACKUP_CONFIRM_QUEUE, user, message1 -> {
                message1.getMessageProperties().setHeader("contentType", "application/json");
                return message1;
            }, correlationData);
        } else {
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(RabbitMQConfigBackupExchange.BACKUP_ORDINARY_EXCHANGE, RabbitMQConfigBackupExchange.BACKUP_CONFIRM_QUEUE + "123", user, message1 -> {
                message1.getMessageProperties().setHeader("contentType", "application/json");
                return message1;
            }, correlationData);
        }
    }

    @Override
    public void priorityMessage(User user, Integer priority) {
        rabbitTemplate.convertAndSend(RabbitMQConfigLazyQueue.EXCHANGE_PRIORITY, RabbitMQConfigLazyQueue.PRIORITY_KEY, user, message -> {
            message.getMessageProperties().setPriority(priority);
            message.getMessageProperties().setHeader("contentType", "application/json");
            return message;
        }, new CorrelationData(UUID.randomUUID().toString()));
    }
}
