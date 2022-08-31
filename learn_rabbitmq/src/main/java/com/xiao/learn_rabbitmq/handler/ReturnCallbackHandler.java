package com.xiao.learn_rabbitmq.handler;

import com.alibaba.fastjson.JSON;
import com.xiao.learn_rabbitmq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 19:10:48
 * @description 未能成功放入到Queue的回调
 */
@Component
@Slf4j(topic = "returnCallbackService")
public class ReturnCallbackHandler implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        String s = new String(returned.getMessage().getBody());
        User user = JSON.parseObject(s, User.class);
        log.info("{} ", user);
        log.info("发送到队列失败,路由键是 {},ack是 {} ,数据是 {} ", returned.getRoutingKey(), returned.getReplyCode(), s);
    }
}
