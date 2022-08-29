package com.xiao.learn_rabbitmq.service;

import com.xiao.learn_rabbitmq.pojo.User;
import org.springframework.amqp.core.Message;
import org.springframework.messaging.handler.annotation.Headers;

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
     *
     * @param user
     *         消息
     * @param sendType
     *         路由键
     */
    void directSend(User user, Integer sendType);

    /**
     * 主题模式，匹配routingkey的方式发布消息
     *
     * @param user
     *         消息
     * @param topic
     *         主题
     */
    void topicSend(User user, String routking);

    /**
     * 死信队列
     *
     * @param user
     */
    void deadSend(User user);

    /**
     * 延迟消费
     *
     * @param user
     */
    void waitConsumer(User user);

    /**
     * 指定延时时间
     *
     * @param user
     * @param waitTime
     */
    void customizeSecond(User user, int waitTime);


    /**
     * 基于插件延迟消费
     *
     * @param user
     * @param waitTime
     */
    void pluginConsumer(User user, int waitTime);
}
