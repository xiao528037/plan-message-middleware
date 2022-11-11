package com.xiao.mqconsumer.consumer;


import com.xiao.mqcommon.entity.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-11 15:47:36
 * @description 消费者
 */
@Slf4j
@Component
public class GeneralMessagesConsumer {

    /**
     * 消费主题为general_topic的所有消息
     */
    @Service
    @RocketMQMessageListener(topic = "general_topic", selectorType = SelectorType.SQL92, selectorExpression = "age >= 12", consumerGroup = "consumer_one_group")
    public class ConsumerOne implements RocketMQListener<MessageExt> {

        @Override
        public void onMessage(MessageExt message) {
            log.info("消费者是 >>> {} 收到的消息是 >>> {} tag >>> {}", "One", new String(message.getBody()), message.getTags());
        }
    }

    /**
     * 接受主题为general_topic，并且tag的主题是tag_n1
     */
    @Service
    @RocketMQMessageListener(topic = "general_topic", selectorExpression = "tag_n1", consumerGroup = "consumer_two_group")
    public class ConsumerTwo implements RocketMQListener<MessageExt> {
        @Override
        public void onMessage(MessageExt message) {
            log.info("消费者是 >>> {} 收到的消息是 >>> {} tag >>> {}", "Two", new String(message.getBody()), message.getTags());
        }
    }

    /**
     * 接受主题为general_topic，并且tag的主题是tag_n2的消息，此消息携带了key
     */
    @Service
    @RocketMQMessageListener(topic = "general_topic", selectorType = SelectorType.SQL92, selectorExpression = "age < 12", consumerGroup = "consumer_three_group")
    public class ConsumerThree implements RocketMQListener<MessageExt> {
        @Override
        public void onMessage(MessageExt message) {
            log.info("消费者是 >>> {} 收到的消息是 >>> {} tag >>> {}", "Three", new String(message.getBody()), message.getTags());
        }
    }
}
