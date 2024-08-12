package org.example.pushMatrix.serviceapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author 泽
 * @Date 2024/8/3 20:49
 *
 */
@Getter
@ToString
@AllArgsConstructor
public enum BusinessCode {
    COMMON_SEND("send", "普通发送"),

    RECALL("recall", "撤回消息");
    /** code 关联着责任链的模板 表示需要进行责任链的业务操作*/
    private String code;

    /** 类型说明 */
    private String description;

}
