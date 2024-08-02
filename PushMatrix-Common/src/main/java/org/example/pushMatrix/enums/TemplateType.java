package org.example.pushMatrix.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author 泽
 * @Date 2024/8/2 22:16
 * 消息模板类型
 */
@AllArgsConstructor
@Getter
public enum TemplateType {
    OPERATION(10, "运营类的模板"),
    TECHNOLOGY(20, "技术类的模板"),
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
