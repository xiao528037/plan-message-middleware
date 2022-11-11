package com.xiao.mqproducer.consumer;

import com.xiao.mqproducer.entity.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-11 15:47:36
 * @description
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "general_two_topic", consumerGroup = "general_two")
public class GeneralMessagesConsumer implements RocketMQListener<TestMessage> {
    @Override
    public void onMessage(TestMessage message) {
        log.info("{} ", message);
    }
}
