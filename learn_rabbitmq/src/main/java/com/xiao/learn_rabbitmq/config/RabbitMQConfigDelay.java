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
 * @createTime 2022-08-29 17:10:13
 * @description 延迟队列，通过普通队列的超时时间和死信队列的方式来实现
 */
@Configuration
public class RabbitMQConfigDelay {
    //两个普通TTL队列的信息
    public final static String QUEUE_WAIT_10 = "wait_ten_queue";
    public final static String QUEUE_WAIT_20 = "wait_twenty_queue";
    public final static String QUEUE_WAIT_CUSTOMIZE = "wait_customize_queue";
    //一个死信队列
    public final static String QUEUE_DEAD_LETTER = "wait_dead_queue";

    //交换机
    public final static String ORDINARY_EXCHANGE = "wait_exchange_ordinary";
    public final static String DEAD_EXCHANGE = "wait_exchange_dead";

    //路由键
    public final static String WAIT_TEN_SECOND_KEY = "wait_ten_second";
    public final static String WAIT_TWENTY_SECOND_KEY = "wait_twenty_second";

    public final static String WAIT_CUSTOMIZE_SECOND_KEY = "wait_customize_second_key";
    public final static String DEAD_KEY = "ttl_dead_key";
    @Resource
    private AmqpAdmin amqpAdmin;

    @PostConstruct
    public void settingAmqpAdmin() {
        //创建队列
        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        parameter.put("x-dead-letter-routing-key", DEAD_KEY);
        parameter.put("x-message-ttl", 10000);
        amqpAdmin.declareQueue(new Queue(QUEUE_WAIT_10, true, false, false, parameter));
        parameter.put("x-message-ttl", 20000);
        amqpAdmin.declareQueue(new Queue(QUEUE_WAIT_20, true, false, false, parameter));
        amqpAdmin.declareQueue(new Queue(QUEUE_DEAD_LETTER, true, false, false, null));
        parameter.put("x-message-ttl", 60000);
        amqpAdmin.declareQueue(new Queue(QUEUE_WAIT_CUSTOMIZE, true, false, false, parameter));
        //创建交换机
        amqpAdmin.declareExchange(new DirectExchange(ORDINARY_EXCHANGE, true, false));
        amqpAdmin.declareExchange(new DirectExchange(DEAD_EXCHANGE, true, false));
        //交换机与队列建立绑定关系
        amqpAdmin.declareBinding(new Binding(QUEUE_WAIT_10, Binding.DestinationType.QUEUE, ORDINARY_EXCHANGE, WAIT_TEN_SECOND_KEY, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_WAIT_20, Binding.DestinationType.QUEUE, ORDINARY_EXCHANGE, WAIT_TWENTY_SECOND_KEY, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_WAIT_CUSTOMIZE, Binding.DestinationType.QUEUE, ORDINARY_EXCHANGE, WAIT_CUSTOMIZE_SECOND_KEY, null));
        amqpAdmin.declareBinding(new Binding(QUEUE_DEAD_LETTER, Binding.DestinationType.QUEUE, DEAD_EXCHANGE, DEAD_KEY, null));
    }
}
