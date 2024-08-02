package org.example.pushMatrix.pojo.entity;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

/**
 *发送短信参数
 */
@Data
@Builder
public class SmsParam {
    /**
     * 业务Id 消息模板的id
     */
    private Long messageTemplateId;
    /**
     * 需要发送的手机号
     */
    private Set<String> phones;

    /**
     * 发送文案的内容
     */
    private String content;
    /**
     * 渠道商Id
     */
    private Integer supplierId;

    /**
     * 渠道商名字
     */
    private String supplierName;
}
