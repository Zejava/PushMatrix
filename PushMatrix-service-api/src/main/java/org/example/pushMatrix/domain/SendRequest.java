package org.example.pushMatrix.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author 泽
 * @Date 2024/8/3 20:52
 * 发送单条消息的接口
 */
@Data
@Accessors(chain = true)
public class SendRequest {
    /**
     * 执行业务类型
     */
    private String code;

    /**
     * 消息模板Id
     */
    private Long messageTemplateId;


    /**
     * 消息相关的参数
     */
    private MessageParam messageParam;

}
