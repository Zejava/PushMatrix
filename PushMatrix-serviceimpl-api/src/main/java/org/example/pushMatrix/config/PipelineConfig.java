package org.example.pushMatrix.config;

import org.example.pushMatrix.action.AfterParamCheckAction;
import org.example.pushMatrix.action.AssembleAction;
import org.example.pushMatrix.action.PreParamCheckAction;
import org.example.pushMatrix.action.SendMqAction;
import org.example.pushMatrix.enums.BusinessCode;
import org.example.pushMatrix.pipeline.BusinessProcess;
import org.example.pushMatrix.pipeline.ProcessController;
import org.example.pushMatrix.pipeline.ProcessTemplate;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/4 8:42
 * 设计执行流程
 */
public class PipelineConfig {
    /**
     * 普通发送执行流程
     * 1. 参数校验 对传入的参数和消息模板等服务端中的参数进行校验
     * 2. 组装参数
     * 3. 发送消息至MQ
     *
     * @return
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        ArrayList<BusinessProcess> processList = new ArrayList<>();

        processList.add(preParamCheckAction());
        processList.add(assembleAction());
        processList.add(afterParamCheckAction());
        processList.add(sendMqAction());

        processTemplate.setProcessList(processList);
        return processTemplate;
    }

    /**
     * pipeline流程控制器
     * 目前暂定只有 普通发送的流程
     * 后续扩展则加BusinessCode和ProcessTemplate
     *
     * @return
     */
    @Bean
    public ProcessController processController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }

    /**
     * 组装参数Action
     *
     * @return
     */
    @Bean
    public AssembleAction assembleAction() {
        return new AssembleAction();
    }

    /**
     * 前置参数校验Action
     *
     * @return
     */
    @Bean
    public PreParamCheckAction preParamCheckAction() {
        return new PreParamCheckAction();
    }

    /**
     * 后置参数校验Action
     *
     * @return
     */
    @Bean
    public AfterParamCheckAction afterParamCheckAction() {
        return new AfterParamCheckAction();
    }

    /**
     * 发送消息至MQ的Action
     * @return
     */
    @Bean
    public SendMqAction sendMqAction() {
        return new SendMqAction();
    }
}
