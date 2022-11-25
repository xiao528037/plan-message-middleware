package com.xiao.message.scsrabbit.utils;

import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-24 18:04:22
 * @description
 */
@Service("myKeySelector")
public class MyKeySelector implements PartitionSelectorStrategy {
    @PostConstruct
    public void init(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    @Override
    public int selectPartition(Object key, int partitionCount) {
        return Integer.parseInt(key.toString()) % partitionCount;
    }
}
