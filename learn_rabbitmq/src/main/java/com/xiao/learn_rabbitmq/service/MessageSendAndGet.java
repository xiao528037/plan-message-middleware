package com.xiao.learn_rabbitmq.service;

import org.springframework.amqp.core.Message;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 11:06:31
 * @description
 */
public interface MessageSendAndGet {

    /**
     * 商品消息
     *
     * @param message
     */
     void commodity(String message);

    /**
     * 商品秒杀信息
     * @param message
     */
    void secKill(String message);

}
