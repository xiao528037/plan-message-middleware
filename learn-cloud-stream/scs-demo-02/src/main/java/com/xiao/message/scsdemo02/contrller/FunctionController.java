package com.xiao.message.scsdemo02.contrller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-24 20:16:28
 * @description
 */
@RestController
@RequestMapping("/message/function")
@Slf4j(topic = "FunctionController")
public class FunctionController {
    private final StreamBridge streamBridge;

    public FunctionController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @GetMapping("send")
    public void send(String message) {
        log.info("收到的消息是 >>> {} ", message);
        streamBridge.send("test1", message);
    }

    @GetMapping("sendTwo")
    public void sendTwo(String message, String message2) {
        log.info("收到的消息是 >>> {} >>>> {}", message, message2);
        streamBridge.send("gather1", message);
        streamBridge.send("gather2", message2);
    }
}
