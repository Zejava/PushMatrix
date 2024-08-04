package org.example.pushMatrix.pipeline;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.enums.RespStatusEnum;
import org.example.pushMatrix.vo.BasicResultVO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
     *
     * @param context
     * @return 返回流程上下文内容
     */
    public ProcessContext process(ProcessContext context) {

        /**
         * 前置检查
         */
        if (!preCheck(context)) {
            return context;
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


    private Boolean preCheck(ProcessContext context) {
        // 上下文
        if (context == null) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.CONTEXT_IS_NULL));
            return false;
        }

        // 业务代码
        String businessCode = context.getCode();
        if (StrUtil.isBlank(businessCode)) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.BUSINESS_CODE_IS_NULL));
            return false;
        }

        // 执行模板
        ProcessTemplate processTemplate = templateConfig.get(businessCode);
        if (processTemplate == null) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_TEMPLATE_IS_NULL));
            return false;
        }

        // 执行模板列表
        List<BusinessProcess> processList = processTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_LIST_IS_NULL));
            return false;
        }

        return true;
    }
}
