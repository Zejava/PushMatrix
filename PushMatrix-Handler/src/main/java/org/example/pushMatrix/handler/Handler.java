package org.example.pushMatrix.handler;

import org.example.pushMatrix.pojo.entity.TaskInfo;

/**
 * @Author 泽
 * @Date 2024/8/2 22:38
 */
public interface Handler {
    boolean doHandler(TaskInfo TaskInfo);
}
