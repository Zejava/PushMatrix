package org.example.pushMatrix.deduplication.build;

import org.example.pushMatrix.domain.DeduplicationParam;
import org.example.pushMatrix.domain.TaskInfo;

/**
 * @Author 泽
 * @Date 2024/8/10 17:38
 */
public interface Builder {
    String CONFIG_PRE = "deduplication_";
    /**
     * 根据配置构建去重参数
     *
     * @param deduplication
     * @param taskInfo
     * @return
     */
    DeduplicationParam build(String deduplication, TaskInfo taskInfo);
}
