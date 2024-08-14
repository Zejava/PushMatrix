package org.example.pushMatrix.common.pipeline;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.common.enums.RespStatusEnum;
import org.example.pushMatrix.common.vo.BasicResultVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author 泽
 * @Date 2024/8/4 8:43
 * 流程上下文控制器
 */
@Slf4j
@Data
@Component
public class ProcessController {
    /**
     * 模板映射
     */
    private Map<String, ProcessTemplate> templateConfig = null;
    /**
     * 执行责任链
     * @param context
     * @return 返回流程上下文内容
     */
    public ProcessContext process(ProcessContext context) {

        /**
         * 前置检查
         */
        try {
            preCheck(context);
        } catch (ProcessException e) {
            return e.getProcessContext();
        }

        /**
         * 遍历流程节点
         */
        List<BusinessProcess> processList = templateConfig.get(context.getCode()).getProcessList();
        for (BusinessProcess businessProcess : processList) {
            businessProcess.process(context);
            if (context.getNeedBreak()) {
                break;
            }
        }
        return context;
    }


    /**
     * 执行前检查，出错则抛出异常
     *
     * @param context 执行上下文
     * @throws ProcessException 异常信息
     */
    private void preCheck(ProcessContext context) throws ProcessException {
        // 上下文
        if (Objects.isNull(context)) {
            context = new ProcessContext();
            context.setResponse(BasicResultVO.fail(RespStatusEnum.CONTEXT_IS_NULL));
            throw new ProcessException(context);
        }

        // 业务代码
        String businessCode = context.getCode();
        if (Objects.isNull(businessCode)) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.BUSINESS_CODE_IS_NULL));
            throw new ProcessException(context);
        }

        // 执行模板
        ProcessTemplate processTemplate = templateConfig.get(businessCode);
        if (Objects.isNull(processTemplate)) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_TEMPLATE_IS_NULL));
            throw new ProcessException(context);
        }

        // 执行模板列表
        List<BusinessProcess> processList = processTemplate.getProcessList();
        if (Objects.isNull(processList) || processList.isEmpty()) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_LIST_IS_NULL));
            throw new ProcessException(context);
        }

    }
}
