package com.xiao.learn_rabbitmq.consumer;

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
    void executor(String message);
}
