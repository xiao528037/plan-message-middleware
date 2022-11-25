package com.xiao.message.scsrabbit.controller;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-24 14:39:07
 * @description
 */
@RestController
@RequestMapping("/scy/message")
public class ScsController {
    private final StreamBridge streamBridge;

    private final Source source;

    private AtomicLong key = new AtomicLong(0);

    public ScsController(StreamBridge streamBridge, Source source) {
        this.streamBridge = streamBridge;
        this.source = source;
    }

    @GetMapping("send")
    public String sendMessage(String message) {
        Message<String> messageBuild = MessageBuilder
                .withPayload(message)
                .setHeader("partition", key.getAndIncrement())
                .build();
//        streamBridge.send("output-out-0", messageBuild);
        source.output().send(messageBuild);
        return String.format("Message sent success >>> %s", message);
    }
}
