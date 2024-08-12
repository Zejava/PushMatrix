package org.example.pushMatrix.handler.deduplication.service;

import org.example.pushMatrix.handler.domain.DeduplicationParam;

/**
 * @Author 泽
 * @Date 2024/8/10 17:45
 */
public interface DeduplicationService {
    /**
     * 去重
     * @param param
     */
    void deduplication(DeduplicationParam param);
}
