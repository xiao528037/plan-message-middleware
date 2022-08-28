package com.xiao.learn_rabbitmq.service;

import com.xiao.learn_rabbitmq.pojo.User;
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
     *
     * @param message
     */
    void secKill(String message);

    /**
     * 批量发送消息
     *
     * @param user
     */
    void sendAllUser(User user);

    /**
     * 更具路由和路由键
     * @param user
     */
    void directSend(User user, Integer sendType);

}
