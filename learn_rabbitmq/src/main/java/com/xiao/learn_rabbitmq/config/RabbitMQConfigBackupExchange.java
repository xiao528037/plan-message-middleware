package com.xiao.learn_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-30 16:01:02
 * @description
 */
@Configuration
public class RabbitMQConfigBackupExchange {

    public final static String BACKUP_CONFIRM_QUEUE = "backup_confirm_queue";

    public final static String BACKUP_NO_CONFIRM_QUEUE = "backup_no_confirm_queue";

    public final static String BACKUP_WARNING_QUEUE = "backup_warning_queue";

    public final static String BACKUP_ORDINARY_EXCHANGE = "backup_ordinary_exchange";

    public final static String BACKUP_EXCHANGE = "backup_exchange";

    @Resource
    private AmqpAdmin amqpAdmin;

    @PostConstruct
    public void settingAmqpAdmin() {
        //创建队列
        amqpAdmin.declareQueue(new Queue(BACKUP_CONFIRM_QUEUE));
        amqpAdmin.declareQueue(new Queue(BACKUP_NO_CONFIRM_QUEUE));
        amqpAdmin.declareQueue(new Queue(BACKUP_WARNING_QUEUE));
        //创建交换机
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("alternate-exchange", BACKUP_EXCHANGE);//绑定备份交换机
        amqpAdmin.declareExchange(new DirectExchange(BACKUP_ORDINARY_EXCHANGE, true, false, arguments));
        amqpAdmin.declareExchange(new FanoutExchange(BACKUP_EXCHANGE));
        //交换机与队列和备份交换机绑定
        amqpAdmin.declareBinding(new Binding(BACKUP_CONFIRM_QUEUE, Binding.DestinationType.QUEUE, BACKUP_ORDINARY_EXCHANGE, BACKUP_CONFIRM_QUEUE, null));
        amqpAdmin.declareBinding(new Binding(BACKUP_NO_CONFIRM_QUEUE, Binding.DestinationType.QUEUE, BACKUP_EXCHANGE, "", null));
        amqpAdmin.declareBinding(new Binding(BACKUP_WARNING_QUEUE, Binding.DestinationType.QUEUE, BACKUP_EXCHANGE, "", null));
    }
}
