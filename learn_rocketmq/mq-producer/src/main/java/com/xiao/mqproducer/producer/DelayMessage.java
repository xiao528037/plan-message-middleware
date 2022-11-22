package com.xiao.mqproducer.producer;

import com.xiao.mqcommon.entity.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 延时消费消息
 */
@Slf4j
@Component
@RequestMapping("/api/v1/delay")
@RestController
public class DelayMessage {
    private AtomicLong count = new AtomicLong();
    private final RocketMQTemplate rocketMQTemplate;


    public DelayMessage(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 使用延时时间
     * @return
     */
    @PostMapping("/send")
    public Object sendDelayMessage() {
        long count = this.count.getAndIncrement();
        TestMessage message = new TestMessage();
        message.setMessageBody("delay message " + count);
        message.setMessageId(count);
        rocketMQTemplate.syncSend("delay1_topic:delay_tag", MessageBuilder.withPayload(message).build(), 3000, 5);
        log.info("{}", new Date());
        return message;
    }
}
