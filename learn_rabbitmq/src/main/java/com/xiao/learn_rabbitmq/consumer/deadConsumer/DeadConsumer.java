package com.xiao.learn_rabbitmq.consumer.deadConsumer;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigDead;
import com.xiao.learn_rabbitmq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.script.Bindings;
import java.io.IOException;
import java.util.Map;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-29 10:55:16
 * @description
 */
//@Component
@Slf4j(topic = "DeadConsumer")
//@RabbitListener(bindings = @QueueBinding(value = @Queue(RabbitMQConfigDead.QUEUE_DIRECT_DEAD), exchange = @Exchange(RabbitMQConfigDead.EXCHANGE_DEAD), key = RabbitMQConfigDead.ROUTING_DEAD_KEY))
public class DeadConsumer {
    @RabbitHandler
    public void consumer(@Payload User msg, Channel channel, Message message, @Headers Map<String, Object> headers) throws IOException {

            log.info("收到消息{} 队列消息 {}", msg, headers.get(AmqpHeaders.CONSUMER_QUEUE));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

    }
}
