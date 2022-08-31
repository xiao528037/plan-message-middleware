package com.xiao.learn_rabbitmq.consumer.backExchangeConsumer;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigBackupExchange;
import com.xiao.learn_rabbitmq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-30 16:26:43
 * @description
 */
@Component
@Slf4j(topic = "confirmConsumer")
public class BackupExchangeConsumer {

    @RabbitListener(queues = RabbitMQConfigBackupExchange.BACKUP_CONFIRM_QUEUE)
    public void confirmQueue(@Payload User user, Channel channel, Message message, @Headers Map Headers) throws IOException {
        log.info(" confirmQueue >>> User {} ", user);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMQConfigBackupExchange.BACKUP_NO_CONFIRM_QUEUE)
    public void noConfirmQueue(@Payload User user, Channel channel, Message message, @Headers Map Headers) throws IOException {
        log.info(" noConfirmQueue >>> User {} ", user);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMQConfigBackupExchange.BACKUP_WARNING_QUEUE)
    public void warningQueue(@Payload User user, Channel channel, Message message, @Headers Map Headers) throws IOException {
        log.info(" warningQueue >>> User {} ", user);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
