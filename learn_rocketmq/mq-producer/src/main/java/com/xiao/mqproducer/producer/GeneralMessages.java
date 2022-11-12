package com.xiao.mqproducer.producer;


import com.xiao.mqcommon.entity.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-11 11:45:14
 * @description 发送普通消息
 */
@RequestMapping("/api/v1/general")
@RestController
@Slf4j
public class GeneralMessages {

    private AtomicLong count = new AtomicLong();
    private final RocketMQTemplate rocketMQTemplate;


    public GeneralMessages(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 发送普通消息
     *
     * @return
     */
    @PostMapping("sendGeneralMessage")
    public TestMessage sendGeneralMessage() {
        String body = "Send general message";
        TestMessage testMessage = new TestMessage();
        testMessage.setMessageId(count.getAndIncrement());
        testMessage.setMessageBody(body + count.get());
        Message<TestMessage> message = MessageBuilder.withPayload(testMessage).build();
        rocketMQTemplate.convertAndSend("general_topic", message);
        return testMessage;
    }

    /**
     * 发送带tag的消息
     *
     * @return
     */
    @PostMapping("sendMessageCarryTag")
    public TestMessage sendMessageCarryTag() {
        String body = "Send carry tag  message";
        TestMessage testMessage = new TestMessage();
        testMessage.setMessageId(count.getAndIncrement());
        testMessage.setMessageBody(body + count.get());
        Message<TestMessage> message = MessageBuilder.withPayload(testMessage).build();
        rocketMQTemplate.convertAndSend("general_topic:tag_n1", message);
        return testMessage;
    }

    /**
     * 发送带tag 和 key的消息
     */
    @PostMapping("/sendMessageCarryTagAndKey")
    public TestMessage send() {
        String body = "Send carry tag and key  message";
        TestMessage message = new TestMessage();
        message.setMessageBody(body + count);
        long count = this.count.getAndIncrement();
        message.setMessageId(count);

        HashMap<String, Object> headers = new HashMap<>(2);
        headers.put("age", count);

        rocketMQTemplate.convertAndSend("general_topic:tag_n2", message, headers);
        return message;
    }
}
