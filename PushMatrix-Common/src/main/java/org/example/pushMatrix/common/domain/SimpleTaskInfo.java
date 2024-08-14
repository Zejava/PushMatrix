package org.example.pushMatrix.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 泽
 * @Date 2024/8/14 21:36
 * 调用发送接口后的返回信息，标记消息的下发情况
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleTaskInfo {
    /**
     * 业务消息发送Id, 用于链路追踪, 若不存在, 则使用 messageId
     */
    private String bizId;

    /**
     * 消息唯一Id(数据追踪使用)
     * 生成逻辑参考 TaskInfoUtils
     */
    private String messageId;

    /**
     * 业务Id(数据追踪使用)
     * 生成逻辑参考 TaskInfoUtils
     */
    private Long businessId;
}
