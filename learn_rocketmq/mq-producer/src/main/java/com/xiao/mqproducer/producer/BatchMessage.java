package com.xiao.mqproducer.producer;


import com.xiao.mqcommon.entity.TestMessage;
import com.xiao.mqproducer.utils.SplitMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 发送批量消息
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/batch")
public class BatchMessage {

    private AtomicLong count = new AtomicLong();
    private final RocketMQTemplate rocketMQTemplate;


    public BatchMessage(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @PostMapping("/send")
    public void send() {
        ArrayList<TestMessage> messageList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            TestMessage testMessage = new TestMessage();
            long count = this.count.getAndIncrement();
            testMessage.setMessageId(count);
            testMessage.setMessageBody("消息条数为 >>> " + count);
            messageList.add(testMessage);
        }
        //消息切割发送
        SplitMessage<TestMessage> iterator = new SplitMessage<>(64 *  1024, messageList);
        while (iterator.hasNext()) {
            rocketMQTemplate.asyncSend("batch_topic:batch_tag", iterator.next(), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("发送成功 >>>> {}");
                }

                @Override
                public void onException(Throwable throwable) {
                    log.info("发送失败 >>>> {}", throwable);
                }
            });
        }

    }
}
