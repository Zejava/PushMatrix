package org.example.pushMatrix.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author 泽
 * @Date 2024/8/2 21:33
 * 所要发送的消息类型枚举
 */
@AllArgsConstructor
@ToString
@Getter
public enum MessageType implements PowerfulEnum {
    NOTICE(10,"通知类消息","notice"),
    MARKETING(20,"营销类消息","marketing"),
    AUTH_CODE(30,"验证码消息","auth_code")
    ;
    private Integer code;
    private String description;
    /**
     * 英文标识
     */
    private String codeEn;
    /**
     * 通过code获取enum
     * @param code
     * @return
     */
}
