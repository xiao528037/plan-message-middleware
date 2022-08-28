package com.xiao.learn_rabbitmq.consumer.directConsumer;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-28 16:51:19
 * @description
 */

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigDirect;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigFanout;
import com.xiao.learn_rabbitmq.consumer.Consumer;
import com.xiao.learn_rabbitmq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j(topic = "fanoutConsumerTwo")
@RabbitListener(
        bindings = @QueueBinding(value =
        @Queue(RabbitMQConfigDirect.QUEUE_DIRECT_THREE),
                exchange = @Exchange(value = RabbitMQConfigDirect.EXCHANGE_DIRECT), key = RabbitMQConfigDirect.ROUTING_KEY_THREE))
public class DirectConsumerTwo extends Consumer {

    @RabbitHandler
    @Override
    public void executor(@Payload User msg, Channel channel, Message message, @Headers Map headers) throws IOException, InterruptedException {
        log.info("收到消息{} 队列消息 {}", msg, headers.get(AmqpHeaders.CONSUMER_QUEUE));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @Override
    public void executor(User msg, Channel channel, Message message) throws IOException, InterruptedException {

    }

}
