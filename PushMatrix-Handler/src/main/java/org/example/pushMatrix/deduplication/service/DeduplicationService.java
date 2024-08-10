package org.example.pushMatrix.deduplication.service;

import org.example.pushMatrix.domain.DeduplicationParam;

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
