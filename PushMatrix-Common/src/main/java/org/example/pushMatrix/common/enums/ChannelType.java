package org.example.pushMatrix.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.pushMatrix.common.dto.EmailContentModel;
import org.example.pushMatrix.common.dto.SmsContentModel;

/**
 * @Author 泽
 * @Date 2024/8/2 21:23
 * 发送消息的渠道类型枚举
 */
@AllArgsConstructor
@ToString
@Getter
public enum ChannelType implements PowerfulEnum{
    //目前实现了短信 todo
    SMS(30, "sms(短信)", SmsContentModel.class,"sms"),
    EMAIL(40, "email(邮件)", EmailContentModel.class,"email"),
//    OFFICIAL_ACCOUNT(50, "OfficialAccounts(服务号)"),
//    MINI_PROGRAM(60, "miniProgram(小程序)")
    ;
    /** 编码的值*/
    private Integer code;
    /** 具体描述 */
    private String description;
    /**内容模型的类*/

    private Class contentModelClass;
    /**
     * 英文标识
     */
    private String codeEn;

//通过编码值获得内容模型反射类的方法
    public static Class getChanelModelClassByCode(Integer code) {
        ChannelType[] values = values();
        for (ChannelType value : values) {
            if (value.getCode().equals(code)) {
                return value.getContentModelClass();
            }
        }
        return null;
    }
    /**
     * 通过code获取enum
     * @param code
     * @return
     */
    public static ChannelType getEnumByCode(Integer code) {
        ChannelType[] values = values();
        for (ChannelType value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
