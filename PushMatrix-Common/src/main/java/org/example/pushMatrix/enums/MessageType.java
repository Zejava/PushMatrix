package org.example.pushMatrix.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author 泽
 * @Date 2024/8/2 21:33
 * 所要发送的消息类型枚举
 */
@AllArgsConstructor
@Getter
public enum MessageType {
    NOTICE(10,"通知类消息"),
    MARKETING(20,"营销类消息"),
    AUTH_CODE(30,"验证码消息")
    ;
    private Integer code;
    private String description;
    public void setCode(Integer code) {
        this.code = code;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
