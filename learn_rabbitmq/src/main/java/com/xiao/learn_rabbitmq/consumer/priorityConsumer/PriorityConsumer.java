package com.xiao.learn_rabbitmq.consumer.priorityConsumer;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigLazyQueue;
import com.xiao.learn_rabbitmq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-30 20:07:07
 * @description
 */
@Component
@Slf4j(topic = "PriorityConsumer")
public class PriorityConsumer {

    @RabbitListener(queues = RabbitMQConfigLazyQueue.QUEUE_PRIORITY)
    public void priority(@Payload User user, Channel channel, Message message, @Headers Map headers) throws IOException {
        log.info("priorityConsumer priority {} >>>  queue >>> {} user >>> {} ", message.getMessageProperties().getPriority(), headers.get(AmqpHeaders.CONSUMER_QUEUE), user);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
