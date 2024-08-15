package org.example.pushMatrix.handler.handler;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import jakarta.annotation.PostConstruct;
import org.example.pushMatrix.common.domain.RecallTaskInfo;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author 泽
 * @Date 2024/8/2 22:38
 * 消息处理器
 */
public interface Handler {

    /**
     * 处理器
     *
     * @param taskInfo
     */
    void doHandler(TaskInfo taskInfo);//处理消息的方法

    /**
     * 撤回消息
     *
     * @param recallTaskInfo
     * @return
     */
    void recall(RecallTaskInfo recallTaskInfo);


}
