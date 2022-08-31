package com.xiao.learn_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-30 19:47:39
 * @description
 */

@Configuration
public class RabbitMQConfigLazyQueue {

    public final static String QUEUE_PRIORITY = "priority_queue";
    public final static String EXCHANGE_PRIORITY = "exchange_priority";
    public final static String PRIORITY_KEY = "priority_key";

    @Resource
    private AmqpAdmin amqpAdmin;

    @PostConstruct
    public void settingAmqpAdmin() {
        //优先队列
        HashMap<String, Object> argument = new HashMap<>();
        argument.put("x-max-priority", 10);
        amqpAdmin.declareQueue(new Queue(QUEUE_PRIORITY, true, false, false, argument));
        //交换机
        amqpAdmin.declareExchange(new DirectExchange(EXCHANGE_PRIORITY));
        //绑定
        amqpAdmin.declareBinding(new Binding(QUEUE_PRIORITY, Binding.DestinationType.QUEUE, EXCHANGE_PRIORITY, PRIORITY_KEY, null));
    }
}
