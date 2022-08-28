package com.xiao.learn_rabbitmq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-08-27 19:28:01
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User  implements Serializable {
    private String id;
    private String username;
}
