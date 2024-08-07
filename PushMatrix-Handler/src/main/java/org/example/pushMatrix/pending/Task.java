package org.example.pushMatrix.pending;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.handler.HandlerHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/7 15:44
 * 任务执行器
 * 0. 丢弃消息 （消息存在问题时）
 * 1 通用去重功能
 * 2. 发送消息
 */
@Data
@Accessors(chain = true)
@Slf4j
@Component
public class Task implements Runnable{
    @Autowired
    private HandlerHolder handlerHolder;
    private TaskInfo taskInfo;

    @Override
    public void run() {
        // 0. 丢弃消息

        // 1. TODO 通用去重

        // 2. 真正发送消息
        handlerHolder.route(taskInfo.getSendChannel())
                .doHandler(taskInfo);
    }
}
