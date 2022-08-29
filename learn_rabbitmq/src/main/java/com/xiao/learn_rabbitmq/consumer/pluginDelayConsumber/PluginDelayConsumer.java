package com.xiao.learn_rabbitmq.consumer.pluginDelayConsumber;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigPluginDelay;
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

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-29 21:02:56
 * @description
 */
@Component
@Slf4j(topic = "PluginDelayConsumer")
@RabbitListener(
        bindings = @QueueBinding(value = @Queue(RabbitMQConfigPluginDelay.PLUGIN_WAIT_QUEUE),
                exchange = @Exchange(RabbitMQConfigPluginDelay.PLUGIN_WAIT_EXCHANGE),
                key = RabbitMQConfigPluginDelay.PLUGIN_WAIT_ROUTING_KEY)
)
public class PluginDelayConsumer {

    @RabbitHandler
    public void pluginDalayConsumer(@Payload User user, Channel channel, Message message, @Headers Map headers) throws IOException {
        log.info("收到消息{} 队列消息 {}", user, headers.get(AmqpHeaders.CONSUMER_QUEUE));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
