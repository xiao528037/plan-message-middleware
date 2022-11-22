package com.xiao.mqconsumer.consumer;

import com.xiao.mqcommon.entity.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class DelayMessageConsumer {
    @Service
    @RocketMQMessageListener(topic = "delay_topic", consumerGroup = "delay_consumer_one")
    public class DelayConsumerMessage implements RocketMQListener<TestMessage> {

        @Override
        public void onMessage(TestMessage message) {
            log.info("延时消息获取 >>> {}", message);
        }
    }
}
