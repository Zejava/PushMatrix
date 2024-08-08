package org.example.pushMatrix.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @Author 泽
 * @Date 2024/8/8 17:13
 * 去重功能需要的参数 去重的消息 去重频率 去重的时间点等
 */
@Builder
@Data
public class DeduplicationParam {
    /**
     * TaskIno信息
     */
    private TaskInfo taskInfo;

    /**
     * 去重时间
     * 单位：秒
     */
    private Long deduplicationTime;

    /**
     * 需达到的次数去重
     */
    private Integer countNum;
}
