package org.example.pushMatrix.handler;

import jakarta.annotation.PostConstruct;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.enums.ChannelType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author 泽
 * @Date 2024/8/2 22:38
 * 发送各个渠道的请求
 */
public abstract class Handler {
    @Autowired
    private HandlerHolder handlerHolder;

    /**
     * 初始化渠道与Handler的映射关系
     * 通过map实现渠道和handler的映射
     */
    @PostConstruct
    private void init() {
        for (ChannelType channelType : ChannelType.values()) {
            handlerHolder.putHandler(channelType.getCode(), this);
        }
    }

    public void doHandler(TaskInfo taskInfo) {
        handler(taskInfo);
    }

    /**
     * 统一处理的handler接口
     *
     * @param taskInfo
     * @return
     */
    public abstract void handler(TaskInfo taskInfo);

}
