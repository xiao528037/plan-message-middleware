package com.xiao.mqproducer.producer;

import com.xiao.mqproducer.entity.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
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

    @PostMapping("/sendOne")
    public Object send() {
        TestMessage testMessage = new TestMessage();
        long andIncrement = count.getAndIncrement();
        testMessage.setMessageId(andIncrement);
        testMessage.setMessageBody("producer " + andIncrement);
        HashMap<String, Object> headers = new HashMap<>(1);
        headers.put("KEYS", "hello");
        MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
        messageHeaderAccessor.setHeader("keys", "hello");
        SendResult sendResult = rocketMQTemplate.syncSend("general_topic:general_tag", MessageBuilder.withPayload(testMessage).setHeaders(messageHeaderAccessor).build());
        log.info("{} ", sendResult);
        return testMessage;
    }

    @PostMapping("/sentTwo")
    public Object sendTwo() {
        long andIncrement = count.getAndIncrement();
        TestMessage testMessage = new TestMessage();
        testMessage.setMessageId(andIncrement);
        testMessage.setMessageBody("sendTwo >> " + andIncrement);
        Map<String, Object> stringObjectMap = new HashMap<>(1);
        stringObjectMap.put("KEYS", andIncrement);
        rocketMQTemplate.convertAndSend("general_two_topic:t1", testMessage, stringObjectMap);
        return testMessage;
    }
}
