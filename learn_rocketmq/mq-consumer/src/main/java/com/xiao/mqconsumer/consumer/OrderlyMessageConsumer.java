package com.xiao.mqconsumer.consumer;


import com.alibaba.fastjson.JSON;
import com.xiao.mqcommon.entity.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;

@Slf4j
@Component
public class OrderlyMessageConsumer {

    private final AtomicLongArray record = new AtomicLongArray(10000);

    private final AtomicInteger index = new AtomicInteger(0);

    /**
     * 主题：orderly tag：tag_0
     */
    @Service
    @RocketMQMessageListener(topic = "orderly_topic", consumeMode = ConsumeMode.ORDERLY, consumerGroup = "orderly_consumer")
    public class OrderlyMessageConsumerZero implements RocketMQListener<MessageExt> {

        @Override
        public void onMessage(MessageExt message) {
            TestMessage testMessage = JSON.parseObject(message.getBody(), TestMessage.class);
            record.addAndGet(index.getAndIncrement(), testMessage.getMessageId());
            log.info("1 <<<< tag {} 消息 {} ", message.getTags(), new String(message.getBody()));
        }
    }

    /**
     * 主题：orderly tag：tag_1
     */
    @Service
    @RocketMQMessageListener(topic = "orderly_topic", consumeMode = ConsumeMode.ORDERLY, consumerGroup = "orderly_consumer")
    public class OrderlyMessageConsumerOne implements RocketMQListener<MessageExt> {

        @Override
        public void onMessage(MessageExt message) {
            TestMessage testMessage = JSON.parseObject(message.getBody(), TestMessage.class);
            record.addAndGet(index.getAndIncrement(), testMessage.getMessageId());
            log.info("2 <<<< tag {} 消息 {} ", message.getTags(), new String(message.getBody()));
        }
    }

    /**
     * 主题：orderly tag：tag_2
     */
    @Service
    @RocketMQMessageListener(topic = "orderly_topic", consumeMode = ConsumeMode.ORDERLY, consumerGroup = "orderly_consumer")
    public class OrderlyMessageConsumerTwo implements RocketMQListener<MessageExt> {

        @Override
        public void onMessage(MessageExt message) {
            TestMessage testMessage = JSON.parseObject(message.getBody(), TestMessage.class);
            record.addAndGet(index.getAndIncrement(), testMessage.getMessageId());
            log.info("3 <<<< tag {} 消息 {} ", message.getTags(), new String(message.getBody()));
        }
    }
}
