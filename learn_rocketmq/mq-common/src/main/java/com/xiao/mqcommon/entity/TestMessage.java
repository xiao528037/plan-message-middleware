package com.xiao.mqcommon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author aloneMan
 * @projectName plan-message-middleware
 * @createTime 2022-11-11 11:43:13
 * @description
 */
@Data
@ToString
@NoArgsConstructor
public class TestMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long messageId;

    private String messageBody;


}
