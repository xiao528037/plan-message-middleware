package com.xiao.learn_rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 15:33:29
 * @description
 */


@Component
@RabbitListener(queues = "learn_queue_a")
@Slf4j(topic = "consumer-one")
public class Consumer1 implements Consumer {

    @Autowired
    private ApplicationContext context;

    @Override
    @RabbitHandler
    public void executor(User msg, Channel channel, Message message) {
        try {
            log.info("端口: {} 消息接受成功 {}", context.getEnvironment().getProperty("server.port"), msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {

            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息一重复处理失败，拒绝再次接受....");
                try {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                log.error("消息即将在次返回队列处理");
                try {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }


        /**
         * 参数1：队列
         * 参数2：true：自动 false：手动
         * 参数3：获取到消息的回调
         * 参数4：取消消息的回调
         */
        /*try {
            channel.basicConsume("learn_queue_a", false, (consumerTag, message1) -> {
                log.info("tag:{} ,context:{} ", consumerTag, message1.getBody().toString());
                channel.basicAck(message1.getEnvelope().getDeliveryTag(), false);
            }, consumerTag -> {
                log.info("{}消息被取消    ", consumerTag);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
}
