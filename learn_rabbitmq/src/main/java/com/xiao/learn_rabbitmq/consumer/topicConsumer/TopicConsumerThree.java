package com.xiao.learn_rabbitmq.consumer.topicConsumer;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigDirect;
import com.xiao.learn_rabbitmq.config.RabbitMQConfigTopic;
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

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-28 18:32:04
 * @description
 */

@Component
@Slf4j(topic = "TopicConsumerOne")
@RabbitListener(
        bindings = @QueueBinding(value =
        @Queue(RabbitMQConfigTopic.QUEUE_TOPIC_THREE),
                exchange = @Exchange(value = RabbitMQConfigTopic.EXCHANGE_TOPIC, type = ExchangeTypes.TOPIC)))
public class TopicConsumerThree {
    @RabbitHandler
    public void executor(@Payload User msg, Channel channel, Message message, @Headers Map headers) throws IOException, InterruptedException {
        log.info("收到消息{} 队列 {} rouking >>> {} = {}", msg, headers.get(AmqpHeaders.CONSUMER_QUEUE), RabbitMQConfigTopic.ROUTING_KEY_THREE+" / "+RabbitMQConfigTopic.ROUTING_KEY_FOUR, msg.getUsername());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
