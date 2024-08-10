package org.example.pushMatrix.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pushMatrix.enums.AnchorState;

/**
 * @Author 泽
 * @Date 2024/8/8 17:13
 * 去重功能需要的参数 去重的消息 去重频率 去重的时间点等
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeduplicationParam {
    /**
     * TaskIno信息
     */
    private TaskInfo taskInfo;

    /**
     * 去重时间
     * 单位：秒
     */
    @JSONField(name = "time")
    private Long deduplicationTime;

    /**
     * 需达到的次数去重
     */
    @JSONField(name = "num")
    private Integer countNum;
    /**
     * 标识属于去重类型(数据埋点)
     */
    private AnchorState anchorState;
}
