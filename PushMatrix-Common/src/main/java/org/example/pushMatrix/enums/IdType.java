package org.example.pushMatrix.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author 泽
 * @Date 2024/8/2 21:30
 * 接收者的id类型枚举
 */
@Getter
@AllArgsConstructor
public enum IdType {
    USER_ID(10, "userid"),//用户id
    DID(20, "did"),//设备id
    PHONE(30, "phone"),//手机号
    OPEN_ID(40, "openId"),//公开id
    EMAIL(50, "email");//邮箱id
    private Integer code;
    private String description;
    public void setCode(Integer code) {
        this.code = code;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
