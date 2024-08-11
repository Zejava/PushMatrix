package org.example.pushMatrix.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @Author 泽
 * @Date 2024/8/3 17:25
 * 发送短信的有关参数 通用
 */
@Data
@Builder
public class SmsParam {
    /**
     * 业务Id 消息模板id
     */
    private Long messageTemplateId;

    /**
     * 需要发送的用户手机号
     */
    private Set<String> phones;

    /**
     * 发送文案
     */
    private String content;

    /**
     * 发送账号
     */
    private Integer sendAccount;
}
