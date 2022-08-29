package com.xiao.learn_rabbitmq.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-28 14:54:21
 * @description 交换机扇出模式
 */

@Configuration
public class RabbitMQConfigFanout {
    public static final String QUEUE_FANOUT_ONE = "queue_fanout_one";
    public static final String QUEUE_FANOUT_TWO = "queue_fanout_two";
    public static final String EXCHANGE_FANOUT = "exchange_fanout";

    public static final String ROUTING_KEY = "FANOUT_KEY";
    @Autowired
    private AmqpAdmin amqpAdmin;

    @PostConstruct
    public void amqpAdmin() {
        //绑定一个fanout状态的交换机
        amqpAdmin.declareExchange(new FanoutExchange(EXCHANGE_FANOUT, true, false));
        //创建两个消息队列
        amqpAdmin.declareQueue(new Queue(QUEUE_FANOUT_ONE, true, false, false));
        amqpAdmin.declareQueue(new Queue(QUEUE_FANOUT_TWO, true, false, false));
        //绑定交换机
        amqpAdmin.declareBinding(new Binding(QUEUE_FANOUT_ONE, Binding.DestinationType.QUEUE, EXCHANGE_FANOUT, ROUTING_KEY, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_FANOUT_TWO, Binding.DestinationType.QUEUE, EXCHANGE_FANOUT, ROUTING_KEY, null));
    }
}
