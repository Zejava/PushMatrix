package org.example.pushMatrix.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/2 21:23
 * 发送消息的渠道类型枚举
 */
@AllArgsConstructor
@Getter
public enum ChannelType {
    //目前实现了短信 todo
    IM(10, "IM(站内信)"),
    PUSH(20, "push(通知栏)"),
    SMS(30, "sms(短信)"),
    EMAIL(40, "email(邮件)"),
    OFFICIAL_ACCOUNT(50, "OfficialAccounts(服务号)"),
    MINI_PROGRAM(60, "miniProgram(小程序)")
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
