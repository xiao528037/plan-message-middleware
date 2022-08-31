package com.xiao.learn_rabbitmq.handler;


import com.alibaba.fastjson.JSON;
import com.xiao.learn_rabbitmq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 18:59:56
 * @description 判断是否成功发送到了Exchange
 */
@Component
@Slf4j(topic = "confirmCallbackService")
public class ConfirmCallbackHandler implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            log.error("消息发送失败,correlationData = {} ,ack = {} cause= {}", correlationData, ack, cause);
        } else {
            ReturnedMessage returned = correlationData.getReturned();
            if (returned!=null){
                String s = new String(returned.getMessage().getBody());
                User user = JSON.parseObject(s, User.class);
                log.info("{} ", user);
            }

            log.info("消息发送成功,correlationData = {} ,ack = {} cause= {}", correlationData, ack, cause);
        }
    }
}
