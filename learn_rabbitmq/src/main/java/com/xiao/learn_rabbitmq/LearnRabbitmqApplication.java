package com.xiao.learn_rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.xiao.learn_rabbitmq.*")
public class LearnRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnRabbitmqApplication.class, args);
    }

}
