package org.example.pushMatrix.handler.pending;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.handler.deduplication.DeduplicationRuleService;
import org.example.pushMatrix.handler.discard.DiscardMessageService;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.handler.handler.HandlerHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Autowired
    private DeduplicationRuleService deduplicationRuleService;
    @Autowired
    private DiscardMessageService discardMessageService;

    @Override
    public void run() {
        // 0. 丢弃消息
        if (discardMessageService.isDiscard(taskInfo)) {
            return;
        }
        // 1.平台通用去重
        deduplicationRuleService.duplication(taskInfo);


        // 2. 真正发送消息
        try {
            handlerHolder.route(taskInfo.getSendChannel())
                    .doHandler(taskInfo);
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
    }
}