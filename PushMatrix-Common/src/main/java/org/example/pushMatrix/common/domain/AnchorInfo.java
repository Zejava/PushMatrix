package org.example.pushMatrix.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author 泽
 * @Date 2024/8/10 17:16
 * 数据埋点的相关信息 跟踪数据情况
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnchorInfo {
    /**
     * 业务发送消息唯一Id(数据追踪使用) 标记业务信息本身
     * 生成逻辑参考 TaskInfoUtils
     */
    private String bizId;

    /**
     * 消息唯一Id(数据追踪使用)
     * 生成逻辑参考 TaskInfoUtils
     */
    private String messageId;
    /**
     * 信息的接收者
     */
    private Set<String> ids;
    /**
     * 业务的具体点位,记录消息当前的情况，被处理还是被去重了等
     */
    private int state;
    /**
     * 业务Id 通过这个id来跟踪数据
     * 生成逻辑参考 TaskInfoUtils
     */
    private Long businessId;
    /**
     * 日志的生成时间
     */
    private long logTimestamp;
}
