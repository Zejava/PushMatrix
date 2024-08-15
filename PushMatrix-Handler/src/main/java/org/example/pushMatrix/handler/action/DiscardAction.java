package org.example.pushMatrix.handler.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.example.pushMatrix.common.constant.FunctionConstant;
import org.example.pushMatrix.common.domain.AnchorInfo;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.enums.AnchorState;
import org.example.pushMatrix.common.pipeline.BusinessProcess;
import org.example.pushMatrix.common.pipeline.ProcessContext;
import org.example.pushMatrix.support.service.ConfigService;
import org.example.pushMatrix.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/14 22:06
 *丢弃消息
 *一般将需要丢弃的模板id写在分布式配置中心
 */
@Service
public class DiscardAction implements BusinessProcess<TaskInfo> {
    private static final String DISCARD_MESSAGE_KEY = "discardMsgIds";//去重消息id

    @Autowired
    private ConfigService config;//获取配置类
    @Autowired
    private LogUtils logUtils;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();
        // 配置示例:	["1","2"]
        //判断消息模板是否在需要去重的消息模板配置中，是的话则打印一条日志来记录这个消息模板被丢弃了，然后中断责任链
        JSONArray array = JSON.parseArray(config.getProperty(DISCARD_MESSAGE_KEY, FunctionConstant.EMPTY_VALUE_JSON_ARRAY));
        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))) {
            logUtils.print(AnchorInfo.builder().bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());
            context.setNeedBreak(true);
        }

    }
}
