package org.example.pushMatrix.serviceapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/3 20:45
 * 消息模板中，使用占位符动态修改模板内容的类
 * 通过两个map映射实现内容的动态更改 占位符通过映射转为消息实体然后把消息实体传入模板中
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageParam {
    /**
     * 业务消息发送Id, 用于链路追踪, 若不存在, austin 则生成一个消息Id
     */
    private String bizId;

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
