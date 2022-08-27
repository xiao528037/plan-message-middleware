package com.xiao.learn_rabbitmq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 19:28:01
 * @description
 */
@Data
@AllArgsConstructor
public class User {
    private String id;
    private String username;
}
