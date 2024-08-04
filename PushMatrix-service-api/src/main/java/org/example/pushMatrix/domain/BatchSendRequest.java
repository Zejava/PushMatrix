package org.example.pushMatrix.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/3 20:43
 * 批量发送接口的参数
 */
@Data
@Accessors(chain = true)
public class BatchSendRequest {
    /**
     * 输入code来执行业务类型，code是用来指代的枚举类型
     *
     */
    private String code;


    /**
     * 消息模板的Id
     * 必传
     */
    private Long messageTemplateId;


    /**
     * 消息相关的参数
     * 必传
     */
    private List<MessageParam> messageParamList;

}
