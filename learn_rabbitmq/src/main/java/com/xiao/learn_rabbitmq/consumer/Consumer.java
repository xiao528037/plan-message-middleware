package com.xiao.learn_rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.xiao.learn_rabbitmq.pojo.User;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 15:40:13
 * @description
 */
public interface Consumer {
    /**
     * 消息处理
     *
     * @param message
     */
    void executor(User msg, Channel channel, Message message) throws IOException, InterruptedException;
}
