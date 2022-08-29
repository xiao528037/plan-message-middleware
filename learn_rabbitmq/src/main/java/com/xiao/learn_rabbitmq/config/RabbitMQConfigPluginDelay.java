package com.xiao.learn_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-29 20:42:16
 * @description 基于插件的延时消费
 */

@Configuration
public class RabbitMQConfigPluginDelay {

    public final static String PLUGIN_WAIT_QUEUE = "plugin_wait_queue";


    public final static String PLUGIN_WAIT_EXCHANGE = "plugin_wait_exchange";

    public final static String PLUGIN_WAIT_ROUTING_KEY = "plugin_wait_key";

    @Resource
    private AmqpAdmin amqpAdmin;

    @PostConstruct
    public void settingAmqpAdmin() {
        amqpAdmin.declareQueue(new Queue(PLUGIN_WAIT_QUEUE, true, false, false));
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-delayed-type", "direct");
//        map.put("x-delay", 60000);
        amqpAdmin.declareExchange(new CustomExchange(PLUGIN_WAIT_EXCHANGE, "x-delayed-message", true, false, map));
        amqpAdmin.declareBinding(new Binding(PLUGIN_WAIT_QUEUE, Binding.DestinationType.QUEUE, PLUGIN_WAIT_EXCHANGE, PLUGIN_WAIT_ROUTING_KEY, null));
    }
}
