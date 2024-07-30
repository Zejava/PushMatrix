package org.example.pojo;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

/**
 *发送短信参数
 */
@Data
@Builder
public class SmsParam {

    /**
     * 需要发送的手机号
     */
    private Set<String> phones;

    /**
     * 发送文案的内容
     */
    private String content;
}
