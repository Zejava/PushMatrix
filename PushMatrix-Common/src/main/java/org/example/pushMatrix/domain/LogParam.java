package org.example.pushMatrix.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 泽
 * @Date 2024/8/10 17:18
 * 日志存放的参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LogParam {

    /**
     * 需要记录的日志
     */
    private Object object;
    /**
     * 标识日志的业务
     */
    private String bizType;
    /**
     * 生成时间
     */
    private long timestamp;
}
