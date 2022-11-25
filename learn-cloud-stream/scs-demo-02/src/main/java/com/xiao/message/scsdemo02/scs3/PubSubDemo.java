package com.xiao.message.scsdemo02.scs3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-24 20:17:23
 * @description
 */
@Configuration
@Slf4j(topic = "PubSubDemo")
public class PubSubDemo {

    private final AtomicLong supplierCount = new AtomicLong(0);
    private final AtomicLong transferCount = new AtomicLong(0);

    @Bean
    public Supplier<String> source() {
        return () -> this.get("source", supplierCount);
    }

    @Bean
    public Function<String, String> transfer() {
        return message -> this.get("transfer", transferCount);
    }

    @Bean
    public Consumer<String> sink() {
        return message -> log.info("sink >> 接收到的消息是 >>>> {} ", message);
    }

    @Bean
    public Function<Tuple2<Flux<String>, Flux<String>>, Flux<String>> gather() {
        return tuple -> {
            Flux<String> t1 = tuple.getT1();
            Flux<String> t2 = tuple.getT2();
            return Flux.merge(t1, t2);
        };
    }

    private String get(String name, AtomicLong count) {
        String message = String.format("消息编号%d", count.getAndIncrement());
        log.info("消息发送方 >>> {} 发送消息 >>> {} ", name, message);
        return message;
    }
}
