package com.xiao.learn_rabbitmq.consumer.deadConsumer;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigDead;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigDirect;
import com.xiao.learn_rabbitmq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-29 10:55:06
 * @description
 */

//@Component
@Slf4j(topic = "OrdinaryConsumer")
//@RabbitListener(bindings = @QueueBinding(value = @Queue(RabbitMQConfigDead.QUEUE_DIRECT_ORDINARY), exchange = @Exchange(RabbitMQConfigDead.EXCHANGE_ORDINARY), key = RabbitMQConfigDead.ROUTING_ORDINARY_KEY))
public class OrdinaryConsumer {

    @RabbitHandler
    public void ordinaryConsumer(@Payload User msg, Channel channel, Message message, @Headers Map<String, Object> headers) throws IOException, InterruptedException {
        if (Integer.parseInt(msg.getId()) % 2 == 0) {
            log.info("收到消息{} 队列消息 {}", msg, headers.get(AmqpHeaders.CONSUMER_QUEUE));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }
}
