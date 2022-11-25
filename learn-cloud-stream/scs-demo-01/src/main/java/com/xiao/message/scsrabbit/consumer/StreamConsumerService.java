package com.xiao.message.scsrabbit.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-24 15:00:09
 * @description
 */
@Service
@Slf4j
public class StreamConsumerService {
    @StreamListener(Sink.INPUT)
    public void process(Object message) {
        log.info("接收到的消息 >>>> {} ", message);
    }
}
