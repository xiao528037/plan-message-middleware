package com.xiao.mqconsumer.consumer;

import com.xiao.mqcommon.entity.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class BatchMessageConsumer {

    private final AtomicLong threadCount = new AtomicLong();
    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000), (runnable) -> {
        return new Thread(runnable, "consumer-t-" + threadCount.getAndIncrement());
    });

    @Service
    @RocketMQMessageListener(topic = "batch_topic", selectorType = SelectorType.TAG, selectorExpression = "batch_tag", consumerGroup = "batch_group")
    public class BatchMessageOne implements RocketMQListener<List<TestMessage>> {

        @Override
        public void onMessage(List<TestMessage> message) {
            for (int i = 0; i < message.size(); i++) {
                int finalI = i;
                poolExecutor.execute(() -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("收到的消息 >>> {}", message.get(finalI));
                });
            }


        }
    }
}
