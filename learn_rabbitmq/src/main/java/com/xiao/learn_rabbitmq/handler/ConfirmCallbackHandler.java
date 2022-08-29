package com.xiao.learn_rabbitmq.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 18:59:56
 * @description 判断是否成功发送到了Broker（rabbimq服务器）
 */
@Component
@Slf4j(topic = "confirmCallbackService")
public class ConfirmCallbackHandler implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            log.error("消息发送失败,correlationData = {} ,ack = {} cause= {}", correlationData, ack, cause);
        } else {
//            log.info("消息发送成功,correlationData = {} ,ack = {} cause= {}", correlationData, ack, cause);
        }
    }
}
