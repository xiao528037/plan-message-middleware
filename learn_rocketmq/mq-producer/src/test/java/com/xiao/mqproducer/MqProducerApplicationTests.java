package com.xiao.mqproducer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@SpringBootTest

@Slf4j
class MqProducerApplicationTests {

    public static final String NAMESRV_ADDR = "192.168.2.110:9876;192.168.2.120:9876";

    /**
     * 同步发送消息
     *
     * @throws MQClientException
     * @throws UnsupportedEncodingException
     * @throws MQBrokerException
     * @throws RemotingException
     * @throws InterruptedException
     */
    @Test
    void producerAndSync() throws MQClientException, UnsupportedEncodingException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("ordinary_group");
        //指定namesrv地址
        defaultMQProducer.setNamesrvAddr(NAMESRV_ADDR);
        //指定超时时间
        defaultMQProducer.setSendMsgTimeout(5000);
        //指定该主题的queue的阁主
        defaultMQProducer.setDefaultTopicQueueNums(2);
        defaultMQProducer.start();
        for (int i = 0; i < 3; i++) {
            Message msg = new Message("OrdinaryTopic_2", "ordA", ("你好我的世界" + 1).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = defaultMQProducer.send(msg);
            log.info("{} %n", sendResult);
        }
        defaultMQProducer.shutdown();
    }

    /**
     * 异步发送消息
     *
     * @throws MQClientException
     */
    @Test
    void producerAndAsync() throws MQClientException, MQBrokerException, RemotingException, InterruptedException, UnsupportedEncodingException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("ordinary_group");
        //指定namesrv地址
        defaultMQProducer.setNamesrvAddr(NAMESRV_ADDR);
        //指定超时时间
        defaultMQProducer.setSendMsgTimeout(5000);
        defaultMQProducer.start();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("OrdinaryTopic", "ordB", ("你好我的世界" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            defaultMQProducer.send(msg, new SendCallback() {
                //收到producer接受到mq发送来的ACK后就会出发回调
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("{}", sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    log.info("exception {}", e.getMessage());
                }
            });
        }

        //休眠等待发送完成
        TimeUnit.SECONDS.sleep(3);
        defaultMQProducer.shutdown();
    }


    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ordinary_group");
        consumer.setNamesrvAddr(NAMESRV_ADDR);
        //指定主题和tag
        consumer.subscribe("OrdinaryTopic", "*");
        //指定从第一条消息开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //指定消费类型是广播还是集群消费(此处是广播模式，默认是集群
        consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    log.info("收到的消息是 >>> {}", msg.getBody());
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
