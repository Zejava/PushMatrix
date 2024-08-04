package org.example.pushMatrix.pipeline;

import lombok.Builder;

/**
 * @Author 泽
 * @Date 2024/8/3 23:05
 * 用来记录流程处理的结果
 */
@Builder
public class ProcessResponse {
    /** 返回值编码 */
    private final String code;

    /** 返回值描述 */
    private final String description;
}
