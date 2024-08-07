package org.example.pushMatrix.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.domain.MessageParam;
import org.example.pushMatrix.domain.SendTaskModel;
import org.example.pushMatrix.enums.RespStatusEnum;
import org.example.pushMatrix.pipeline.BusinessProcess;
import org.example.pushMatrix.pipeline.ProcessContext;
import org.example.pushMatrix.vo.BasicResultVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 泽
 * @Date 2024/8/4 8:37
 * 对前置参数进行校验
 */
@Slf4j
public class PreParamCheckAction implements BusinessProcess {
    @Override
    public void process(ProcessContext context) {
        SendTaskModel sendTaskModel = (SendTaskModel) context.getProcessModel();

        Long messageTemplateId = sendTaskModel.getMessageTemplateId();
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
//对消息模板以及消息参数的内容进行校验，如果不存在模板或内容为空 则记录错误信息
        if (messageTemplateId == null || CollUtil.isEmpty(messageParamList)) {
            context.setNeedBreak(true);
            context.setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return ;
        }

        // 过滤接收者为null的messageParam
        List<MessageParam> resultMessageParamList = messageParamList.stream()
                .filter(messageParam -> !StrUtil.isBlank(messageParam.getReceiver()))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(resultMessageParamList)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }

        sendTaskModel.setMessageParamList(resultMessageParamList);
    }
}
