package org.example.pushMatrix.handler.domain;

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

    /**
     * 发送账号的id（如果短信模板指定了发送账号，则该字段有值）
     * <p>
     * 如果有账号id，那就用账号id 检索
     * 如果没有账号id，那就根据 com.java3y.austin.handler.domain.sms.SmsParam#scriptName 检索
     */
    private Integer sendAccountId;

    /**
     * 渠道账号的脚本名标识
     */
    private String scriptName;
}
