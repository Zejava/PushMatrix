package org.example.pushMatrix.handler.handler;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import jakarta.annotation.PostConstruct;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author 泽
 * @Date 2024/8/2 22:38
 * 发送各个渠道的请求
 */
public abstract class Handler {
    /**
     * 对应渠道的code
     * 子类初始化的时候进行指定
     */
    protected Integer channelCode;
    @Autowired
    private HandlerHolder handlerHolder;

    /**
     * 初始化渠道与Handler的映射关系
     * 通过map实现渠道和handler的映射
     */
    @PostConstruct
    private void init() {
            handlerHolder.putHandler(channelCode, this);
    }

    public void doHandler(TaskInfo taskInfo) throws TencentCloudSDKException {
        handler(taskInfo);
    }

    /**
     * 统一处理的handler接口
     *
     * @param taskInfo
     * @return
     */
    public abstract void handler(TaskInfo taskInfo) throws TencentCloudSDKException;

}
