package org.example.pushMatrix.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author 泽
 * @Date 2024/8/2 22:16
 * 消息模板类型
 */
@AllArgsConstructor
@Getter
@ToString
public enum TemplateType {
    CLOCKING(10, "定时类的模板(后台定时调用)"),
    REALTIME(20, "实时类的模板(接口实时调用)"),
    ;


    private Integer code;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

}
