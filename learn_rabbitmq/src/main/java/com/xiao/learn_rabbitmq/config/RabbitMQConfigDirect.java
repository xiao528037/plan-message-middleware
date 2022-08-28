package com.xiao.learn_rabbitmq.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-28 17:08:28
 * @description
 */
@Configuration
public class RabbitMQConfigDirect {

    public static final String QUEUE_DIRECT_ONE = "queue_direct_one";
    public static final String QUEUE_DIRECT_TWO = "queue_direct_two";
    public static final String QUEUE_DIRECT_THREE = "queue_direct_three";
    public static final String EXCHANGE_DIRECT = "exchange_direct";

    public static final String ROUTING_KEY_ONE = "direct_key_one";
    public static final String ROUTING_KEY_TWO = "direct_key_two";
    public static final String ROUTING_KEY_THREE = "direct_key_three";

    @Autowired
    private AmqpAdmin amqpAdmin;

    @PostConstruct
    public void settingAmqpAdmin() {
        amqpAdmin.declareExchange(new DirectExchange(EXCHANGE_DIRECT, true, false));
        amqpAdmin.declareQueue(new Queue(QUEUE_DIRECT_ONE, true));
        amqpAdmin.declareQueue(new Queue(QUEUE_DIRECT_TWO, true));
        amqpAdmin.declareQueue(new Queue(QUEUE_DIRECT_THREE, true));

        //1。队列名 2。队列类型 3。交换机 4。routingkey 5。其他参数
        amqpAdmin.declareBinding(new Binding(QUEUE_DIRECT_ONE, Binding.DestinationType.QUEUE, EXCHANGE_DIRECT, ROUTING_KEY_ONE, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_DIRECT_TWO, Binding.DestinationType.QUEUE, EXCHANGE_DIRECT, ROUTING_KEY_TWO, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_DIRECT_THREE, Binding.DestinationType.QUEUE, EXCHANGE_DIRECT, ROUTING_KEY_THREE, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_DIRECT_ONE, Binding.DestinationType.QUEUE, EXCHANGE_DIRECT, ROUTING_KEY_THREE, null));
    }
}
