package org.example.pushMatrix.serviceimplapi.config;

import org.example.pushMatrix.serviceapi.enums.BusinessCode;
import org.example.pushMatrix.common.pipeline.ProcessController;
import org.example.pushMatrix.common.pipeline.ProcessTemplate;
import org.example.pushMatrix.serviceimplapi.action.send.SendAfterCheckAction;
import org.example.pushMatrix.serviceimplapi.action.send.SendAssembleAction;
import org.example.pushMatrix.serviceimplapi.action.send.SendMqAction;
import org.example.pushMatrix.serviceimplapi.action.send.SendPreCheckAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/4 8:42
 * 设计执行流程
 */
@Configuration
public class PipelineConfig {
    @Autowired
    private SendPreCheckAction sendPreCheckAction;
    @Autowired
    private SendAssembleAction sendAssembleAction;
    @Autowired
    private SendAfterCheckAction sendAfterCheckAction;
    @Autowired
    private SendMqAction sendMqAction;
    /**
     * 普通发送执行流程
     * 1. 前置参数校验
     * 2. 组装参数
     * 3. 后置参数校验
     * 4. 发送消息至MQ
     *
     * @return
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(sendPreCheckAction, sendAssembleAction,
                sendAfterCheckAction, sendMqAction));
        return processTemplate;
    }


    /**
     * pipeline流程控制器
     * 目前暂定只有 普通发送的流程
     * 后续扩展则加BusinessCode和ProcessTemplate
     *
     * @return
     */
    @Bean("apiProcessController")
    public ProcessController apiProcessController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }
}
