package org.example.pushMatrix.handler.shield;

import org.example.pushMatrix.common.domain.TaskInfo;

/**
 * @Author 泽
 * @Date 2024/8/14 19:06
 * 夜间屏蔽消息服务
 */
public interface ShieldService {
    void shield(TaskInfo taskInfo);
}
