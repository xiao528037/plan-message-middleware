package com.xiao.message.learn_rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class LearnRocketmqApplicationTests {

	@Test
	void contextLoads() {
		DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
		defaultMQProducer.setNamesrvAddr("192.168.3.11:9876");
	}

}
