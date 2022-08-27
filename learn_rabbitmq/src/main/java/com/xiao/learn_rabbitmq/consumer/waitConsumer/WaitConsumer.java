package com.xiao.learn_rabbitmq.consumer.waitConsumer;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigDelay;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigDirect;
import com.xiao.learn_rabbitmq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.Map;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-29 17:46:43
 * @description
 */


@Component
@Slf4j(topic = "WaitConsumer")
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(RabbitMQConfigDelay.QUEUE_DEAD_LETTER),
                exchange = @Exchange(value = RabbitMQConfigDelay.DEAD_EXCHANGE), key = RabbitMQConfigDelay.DEAD_KEY))
public class WaitConsumer {

    @RabbitHandler
    public void executor(@Payload User msg, Channel channel, Message message, @Headers Map headers) throws IOException, InterruptedException {
        log.info("收到消息{} 队列消息 {}", msg, headers.get(AmqpHeaders.CONSUMER_QUEUE));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
