package com.xiao.learn_rabbitmq.rabbitmq;

import com.xiao.learn_rabbitmq.pojo.User;
import com.xiao.learn_rabbitmq.service.MessageSendAndGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 11:17:47
 * @description
 */

@RestController
@RequestMapping("/rabbitmq")
@Api(tags = "rabbitmq生产者和消费者")
public class RabbitMQController {

    private MessageSendAndGet messageSendAndGet;

    private static AtomicInteger requestCount = new AtomicInteger(0);

    public RabbitMQController(MessageSendAndGet messageSendAndGet) {
        this.messageSendAndGet = messageSendAndGet;
    }

    @PostMapping("/commodity")
    @ApiOperation("商品消息")
    public void commodity(String message) {
        messageSendAndGet.commodity(Integer.toString(requestCount.incrementAndGet()));
    }

    @PostMapping("/secKill")
    @ApiOperation("秒杀消息")
    public void secKill(String message) {
        messageSendAndGet.secKill(Integer.toString(requestCount.incrementAndGet()));
    }

    @PostMapping("/notifyAllUser")
    @ApiOperation("通知用户")
    public void notifyAllUser(User user) {
        messageSendAndGet.sendAllUser(user);
    }

    @PostMapping("/direct_routkey")
    @ApiOperation("路由发送")
    public void directRoutingKey(Integer sendType) {
        User user = new User(Integer.toString(requestCount.incrementAndGet()), UUID.randomUUID().toString());
        messageSendAndGet.directSend(user, sendType);
    }

    @PostMapping("/topic")
    @ApiOperation("路由匹配发送")
    public void topicRoutingKey(String routingKey) {
        User user = new User();
        user.setUsername(routingKey);
        user.setId(Integer.toString(requestCount.incrementAndGet()));
        messageSendAndGet.topicSend(user, routingKey);
    }

    @PostMapping("/deadLetter")
    @ApiOperation(("死信队列"))
    public void deadLetter() {
        User user = new User(Integer.toString(requestCount.incrementAndGet()), UUID.randomUUID().toString());
        messageSendAndGet.deadSend(user);
    }

    @PostMapping("waitMessage")
    @ApiOperation("延迟消费")
    public void waitConsumer() {
        User user = new User(Integer.toString(requestCount.incrementAndGet()), UUID.randomUUID().toString());
        messageSendAndGet.waitConsumer(user);
    }
}
