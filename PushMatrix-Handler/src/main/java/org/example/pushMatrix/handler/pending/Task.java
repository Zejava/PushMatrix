package org.example.pushMatrix.handler.pending;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.common.pipeline.ProcessContext;
import org.example.pushMatrix.common.pipeline.ProcessController;
import org.example.pushMatrix.common.pipeline.ProcessModel;
import org.example.pushMatrix.common.vo.BasicResultVO;
import org.example.pushMatrix.handler.config.TaskPipelineConfig;

import org.example.pushMatrix.common.domain.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author 泽
 * @Date 2024/8/7 15:44
 * 任务执行器 开启任务线程的类
 * 0. 丢弃消息 （消息存在问题时）
 * 1 通用去重功能
 * 2. 发送消息
 */
@Data
@Accessors(chain = true)
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable{
    @Autowired
    @Qualifier("handlerProcessController")
    private ProcessController processController;
    private TaskInfo taskInfo;


    @Override
    public void run() {//启动责任链 使任务传入开始工作
        ProcessContext<ProcessModel> context = ProcessContext.builder()
                .processModel(taskInfo).code(TaskPipelineConfig.PIPELINE_HANDLER_CODE)
                .needBreak(false).response(BasicResultVO.success())
                .build();
        processController.process(context);
    }
}
