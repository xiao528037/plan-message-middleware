package com.xiao.learn_rabbitmq.config;


import com.xiao.learn_rabbitmq.handler.ConfirmCallbackHandler;
import com.xiao.learn_rabbitmq.handler.ReturnCallbackHandler;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;


/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 11:23:59
 * @description
 */
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "learn_queue";

    public static final String EXCHANGE_NAME = "learn_exchange";

    public static final String ROUTING_KEY = "learn_queue_one";


    @Bean
    public Queue queueCommodity() {
        return new Queue(QUEUE_NAME + "_a", true, false, false);
    }

    @Bean
    public Queue queueSecKill() {
        return new Queue(QUEUE_NAME + "_b", true, false, false);
    }

    @Bean
    public DirectExchange exchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).build();
    }

    @Bean
    public Binding bindingCommodity() {
        return BindingBuilder.bind(queueCommodity()).to(exchange()).with(ROUTING_KEY + "_a");
    }

    @Bean
    Binding bindingSecKill() {
        return BindingBuilder.bind(queueSecKill()).to(exchange()).with(ROUTING_KEY + "_b");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1:5672");
        connectionFactory.setUsername("aloneman");
        connectionFactory.setPassword("aloneman");
        connectionFactory.setVirtualHost("learn_1");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        RabbitProperties.SimpleContainer simpleContainer = new RabbitProperties.SimpleContainer();
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ConfirmCallbackHandler confirmCallbackHandler, ReturnCallbackHandler returnCallbackHandler) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        //配置发送到Broker回调
        template.setConfirmCallback(confirmCallbackHandler);
        //配置成功消费回调
        template.setReturnsCallback(returnCallbackHandler);
        return template;
    }


    /**
     * 自动发送JSON结构或消费时自动将JSON转成相应的对象
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory =
                new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setPrefetchCount(0);
        //设置消费端手动ack
        simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(simpleRabbitListenerContainerFactory, connectionFactory);
        return simpleRabbitListenerContainerFactory;
    }

       /* @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }*/
}
