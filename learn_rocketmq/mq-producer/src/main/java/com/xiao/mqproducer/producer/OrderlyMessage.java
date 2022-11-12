package com.xiao.mqproducer.producer;

import com.xiao.mqcommon.entity.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 有序消息
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/orderly")
public class OrderlyMessage {

    private final RocketMQTemplate rocketMQTemplate;
    private AtomicLong count = new AtomicLong();

    private List<String> messageOrderly = Arrays.asList("前", "中", "后");

    public OrderlyMessage(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @PostMapping("send")
    public Object sendOrderly() throws InterruptedException {
        long count = this.count.getAndIncrement();
        ArrayList<Object> messageList = new ArrayList<>();
        for (String s : messageOrderly) {
            TestMessage message = new TestMessage();
            message.setMessageId(count);
            message.setMessageBody(s);
            messageList.add(message);
            String destination = "orderly_topic:orderly_tag_" + (count % 3);
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(destination, message, String.valueOf(count));
            log.info("{}", sendResult);
        }
        return messageList;
    }

}
