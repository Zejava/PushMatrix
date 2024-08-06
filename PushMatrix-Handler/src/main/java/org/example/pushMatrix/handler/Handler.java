package org.example.pushMatrix.handler;

import org.example.pushMatrix.domain.TaskInfo;

/**
 * @Author 泽
 * @Date 2024/8/2 22:38
 * 发送各个渠道的请求
 */
public interface Handler {
    /**
     * 统一处理请求的接口
     * @param TaskInfo
     * @return
     */
    boolean doHandler(TaskInfo TaskInfo);
}
