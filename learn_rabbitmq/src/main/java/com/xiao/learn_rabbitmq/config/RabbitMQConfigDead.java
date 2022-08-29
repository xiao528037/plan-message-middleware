package com.xiao.learn_rabbitmq.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-29 10:16:31
 * @description 死信队列
 */
@Configuration
public class RabbitMQConfigDead {
    public static final String QUEUE_DIRECT_ORDINARY = "queue_direct_ordinary";
    public static final String QUEUE_DIRECT_DEAD = "queue_direct_dead";
    public static final String EXCHANGE_ORDINARY = "exchange_ordinary";
    public static final String EXCHANGE_DEAD = "exchange_dead";
    public static final String ROUTING_ORDINARY_KEY = "ORDINARY_KEY";
    public static final String ROUTING_DEAD_KEY = "DEAD_KEY";
    @Resource
    private AmqpAdmin amqpAdmin;

    @PostConstruct
    public void settingAmqpAdmin() {
        //死信队列
        amqpAdmin.declareQueue(new Queue(QUEUE_DIRECT_DEAD, true, false, false, null));
        //普通队列和死信队列建立关系，当出现消费失败、超时等异常，超过指定时间就进入死信队列
        HashMap<String, Object> map = new HashMap<>();
        //绑定死信交换机
        map.put("x-dead-letter-exchange", EXCHANGE_DEAD);
        //绑定死信routingkey，去找死信交换机下的queue
        map.put("x-dead-letter-routing-key", ROUTING_DEAD_KEY);
        //配置queue最大长度，超过这个长度，则进入死信队列
        map.put("x-max-length", 6);
        //配置超时时间，如果指定时间没有被消费，放入到死信队列
        map.put("x-message-ttl", 20000);
        amqpAdmin.declareQueue(new Queue(QUEUE_DIRECT_ORDINARY, true, false, false, map));
        //创建交换机
        amqpAdmin.declareExchange(new DirectExchange(EXCHANGE_ORDINARY, true, false));
        amqpAdmin.declareExchange(new DirectExchange(EXCHANGE_DEAD, true, false));
        //队列和交换机绑定
        amqpAdmin.declareBinding(new Binding(QUEUE_DIRECT_ORDINARY, Binding.DestinationType.QUEUE, EXCHANGE_ORDINARY, ROUTING_ORDINARY_KEY, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_DIRECT_DEAD, Binding.DestinationType.QUEUE, EXCHANGE_DEAD, ROUTING_DEAD_KEY, null));
    }
}
