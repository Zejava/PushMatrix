package org.example.pushMatrix.handler.handler;

import jakarta.annotation.PostConstruct;
import org.example.pushMatrix.common.domain.AnchorInfo;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.enums.AnchorState;
import org.example.pushMatrix.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author 泽
 * @Date 2024/8/14 22:21
 * 发送各个渠道的handler
 */
public abstract class BaseHandler implements Handler{
    @Autowired
    private HandlerHolder handlerHolder;
    /**
     * 标识渠道的Code
     * 子类初始化的时候指定
     */
    protected Integer channelCode;
    /**
     * 初始化渠道与Handler的映射关系
     */
    @PostConstruct
    private void init() {
        handlerHolder.putHandler(channelCode, this);
    }

    /**
     * 统一处理的handler接口
     *
     * @param taskInfo
     * @return
     */
    public abstract boolean handler(TaskInfo taskInfo);
    @Override
    public void doHandler(TaskInfo taskInfo) {
        if (handler(taskInfo)) {
            LogUtils.print(AnchorInfo.builder().state(AnchorState.SEND_SUCCESS.getCode()).bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).build());
            return;
        }
        LogUtils.print(AnchorInfo.builder().state(AnchorState.SEND_FAIL.getCode()).bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).build());
    }

}
