package org.example.pushMatrix.cron.service;

/**
 * @Author 泽
 * @Date 2024/8/13 22:24
 * 具体实现处理定时任务的Handler
 */
public interface TaskHandler {
    /**
     * 处理具体的逻辑
     *
     * @param messageTemplateId
     */
    void handle(Long messageTemplateId);
}
