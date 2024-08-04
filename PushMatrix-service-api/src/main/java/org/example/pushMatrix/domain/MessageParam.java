package org.example.pushMatrix.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/3 20:45
 */
@Data
@Accessors(chain = true)
public class MessageParam {
    /**
     * 接收者
     * 多个用,逗号号分隔开
     *
     */
    private String receiver;
    /**
     * 消息内容中的可变部分 比如用户在消息模板中输入：消息{$comment}
     * 然后{}中的内容通过这个map映射实现
     * 可选
     */
    private Map<String, String> variables;

    /**
     *  扩展参数 跟上面那个参数相同，实现了消息模板中，占位符动态变更消息内容
     * 可选
     */
    private Map<String,String> extra;
}
