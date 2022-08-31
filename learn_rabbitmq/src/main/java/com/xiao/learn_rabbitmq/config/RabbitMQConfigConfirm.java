package com.xiao.learn_rabbitmq.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-30 11:46:55
 * @description
 */

@Configuration
public class RabbitMQConfigConfirm {
    public final static String CONFIRM_QUEUE = "confirm_queue";
    public final static String CONFIRM_EXCHANGE = "confirm_exchange";
    public final static String CONFIRM_QUEUE_KEY = "confirm_queue_key";

    @Resource
    public AmqpAdmin amqpAdmin;

    @PostConstruct
    public void intiAmqpAdmin() {
        //创建队列
        amqpAdmin.declareQueue(new Queue(CONFIRM_QUEUE, true, false, false, null));
        //创建交换机
        amqpAdmin.declareExchange(new DirectExchange(CONFIRM_EXCHANGE, true, false));
        //绑定交换机
        amqpAdmin.declareBinding(new Binding(CONFIRM_QUEUE, Binding.DestinationType.QUEUE, CONFIRM_EXCHANGE, CONFIRM_QUEUE_KEY, null));
    }
}
