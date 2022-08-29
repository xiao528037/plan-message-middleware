package com.xiao.learn_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-28 18:17:49
 * @description
 */
@Configuration
public class RabbitMQConfigTopic {
    public static final String QUEUE_TOPIC_ONE = "queue_topic_one";
    public static final String QUEUE_TOPIC_TWO = "queue_topic_two";
    public static final String QUEUE_TOPIC_THREE = "queue_topic_three";
    public static final String EXCHANGE_TOPIC = "exchange_topic";

    public static final String ROUTING_KEY_ONE = "*.java.*";
    public static final String ROUTING_KEY_TWO = "#.log.error";
    public static final String ROUTING_KEY_THREE = "*.*.warning";
    public static final String ROUTING_KEY_FOUR = "#.log.#";

    @Autowired
    private AmqpAdmin amqpAdmin;

    @PostConstruct
    public void settingAmqpAdmin() {
        //创建一个交换机
        amqpAdmin.declareExchange(new TopicExchange(EXCHANGE_TOPIC, true, false));
        //创建三个队列
        amqpAdmin.declareQueue(new Queue(QUEUE_TOPIC_ONE));
        amqpAdmin.declareQueue(new Queue(QUEUE_TOPIC_TWO));
        amqpAdmin.declareQueue(new Queue(QUEUE_TOPIC_THREE));
        //绑定routingkey
        amqpAdmin.declareBinding(new Binding(QUEUE_TOPIC_ONE, Binding.DestinationType.QUEUE, EXCHANGE_TOPIC, ROUTING_KEY_ONE, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_TOPIC_TWO, Binding.DestinationType.QUEUE, EXCHANGE_TOPIC, ROUTING_KEY_TWO, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_TOPIC_THREE, Binding.DestinationType.QUEUE, EXCHANGE_TOPIC, ROUTING_KEY_THREE, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_TOPIC_THREE, Binding.DestinationType.QUEUE, EXCHANGE_TOPIC, ROUTING_KEY_FOUR, null));
    }
}
