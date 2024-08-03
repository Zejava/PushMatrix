package org.example.pushMatrix.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author 泽
 * @Date 2024/8/2 21:34
 */
@AllArgsConstructor
@Getter
@ToString
public enum SmsStatus {
    SEND_SUCCESS(10,"调用渠道接口发送成功"),
    RECEIVE_SUCCESS(20,"用户收到短信(收到渠道短信回执，状态成功)"),
    RECEIVE_FAIL(30, "用户收不到短信(收到渠道短信回执，状态失败)");
    private Integer code;
    private String description;
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}
