package org.example.pushMatrix.serviceimplapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pushMatrix.serviceapi.domain.MessageParam;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.pipeline.ProcessModel;
import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/3 23:10
 *  发送消息的任务模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendTaskModel implements ProcessModel {
    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 请求参数
     */
    private List<MessageParam> messageParamList;

    /**
     * 发送任务的信息
     */
    private List<TaskInfo> taskInfo;
}
