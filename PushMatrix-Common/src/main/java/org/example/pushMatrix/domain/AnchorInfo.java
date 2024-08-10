package org.example.pushMatrix.domain;

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
     * 信息的接送者
     */
    private Set<String> ids;
    /**
     * 业务的具体点位
     */
    private int state;
    /**
     * 业务Id 通过这个id来跟踪用户数据
     * 生成逻辑参考 TaskInfoUtils
     */
    private Long businessId;
    /**
     * 生成时间
     */
    private long timestamp;
}
