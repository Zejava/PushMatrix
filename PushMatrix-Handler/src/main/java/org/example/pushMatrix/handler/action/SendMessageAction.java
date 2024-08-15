package org.example.pushMatrix.handler.action;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Sets;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.pipeline.BusinessProcess;
import org.example.pushMatrix.common.pipeline.ProcessContext;
import org.example.pushMatrix.handler.handler.HandlerHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/14 22:15
 * 发送消息，路由到对应的渠道去下发消息
 */
@Service
public class SendMessageAction implements BusinessProcess<TaskInfo> {
    @Autowired
    private HandlerHolder handlerHolder;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();
        handlerHolder.route(taskInfo.getSendChannel()).doHandler(taskInfo);
    }
}
