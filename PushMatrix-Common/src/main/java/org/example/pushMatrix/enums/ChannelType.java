package org.example.pushMatrix.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.pushMatrix.dto.SmsContentModel;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/2 21:23
 * 发送消息的渠道类型枚举
 */
@AllArgsConstructor
@ToString
@Getter
public enum ChannelType {
    //目前实现了短信 todo
    SMS(30, "sms(短信)", SmsContentModel.class),
//    EMAIL(40, "email(邮件)",),
//    OFFICIAL_ACCOUNT(50, "OfficialAccounts(服务号)"),
//    MINI_PROGRAM(60, "miniProgram(小程序)")
    ;
    private Integer code;
    private String description;
    //添加一个

    private Class contentModelClass;


    public static Class getChanelModelClassByCode(Integer code) {
        ChannelType[] values = values();
        for (ChannelType value : values) {
            if (value.getCode().equals(code)) {
                return value.getContentModelClass();
            }
        }
        return null;
    }
}
