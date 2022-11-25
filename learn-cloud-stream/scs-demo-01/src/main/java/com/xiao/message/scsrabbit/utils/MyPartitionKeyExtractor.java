package com.xiao.message.scsrabbit.utils;

import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-24 18:06:29
 * @description
 */
@Service("myPartitionKeyExtractor")
public class MyPartitionKeyExtractor implements PartitionKeyExtractorStrategy {

    @PostConstruct
    public void init(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    private static final String PARTION_PROP = "partition";

    @Override
    public Object extractKey(Message<?> message) {
        return message.getHeaders().get(MyPartitionKeyExtractor.PARTION_PROP);
    }
}
