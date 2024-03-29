package com.xiao.learn_rabbitmq.config;


import com.xiao.learn_rabbitmq.handler.ConfirmCallbackHandler;
import com.xiao.learn_rabbitmq.handler.FastJsonMessageConverter;
import com.xiao.learn_rabbitmq.handler.ReturnCallbackHandler;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;


/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 11:23:59
 * @description
 */
@Configuration
public class RabbitMQConfig {


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1:5672");
        connectionFactory.setUsername("aloneman");
        connectionFactory.setPassword("aloneman");
        connectionFactory.setVirtualHost("learn_1");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ConfirmCallbackHandler confirmCallbackHandler, ReturnCallbackHandler returnCallbackHandler) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        //returnCallBack时使用一个标记
        template.setMandatory(true);
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
    public FastJsonMessageConverter fastJsonMessageConverter() {
        return new FastJsonMessageConverter();
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory =
                new SimpleRabbitListenerContainerFactory();
        configurer.configure(simpleRabbitListenerContainerFactory, connectionFactory);
        //设置消费端手动ack
        simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleRabbitListenerContainerFactory.setReceiveTimeout(3000L);
        //默认消费者数量
//        simpleRabbitListenerContainerFactory.setConcurrentConsumers(3);
        //最大消费者数量
//        simpleRabbitListenerContainerFactory.setMaxConcurrentConsumers(10);
        //每次给消费这信道中放入的消息数量
        simpleRabbitListenerContainerFactory.setPrefetchCount(3);
        return simpleRabbitListenerContainerFactory;
    }


    // 可以将json串反序列化为对象
/*    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer() {
        return registrar -> {
            registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
        };
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(mappingJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }*/
}
