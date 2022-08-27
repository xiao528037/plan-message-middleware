package com.xiao.learn_rabbitmq.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 19:10:48
 * @description
 */
@Component
@Slf4j(topic = "returnCallbackService")
public class ReturnCallbackHandler implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("{} ", returned.toString());
    }
}
