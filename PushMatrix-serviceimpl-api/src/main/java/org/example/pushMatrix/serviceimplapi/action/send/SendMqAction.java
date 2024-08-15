package org.example.pushMatrix.serviceimplapi.action.send;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.common.domain.SimpleTaskInfo;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.enums.RespStatusEnum;
import org.example.pushMatrix.common.pipeline.BusinessProcess;
import org.example.pushMatrix.common.pipeline.ProcessContext;
import org.example.pushMatrix.common.vo.BasicResultVO;
import org.example.pushMatrix.serviceimplapi.domain.SendTaskModel;
import org.example.pushMatrix.serviceimplapi.service.SendMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 泽
 * @Date 2024/8/15 10:53
 * 1.消息传给MQ
 * 2.将拼装好的messageId给到接口调用方
 */
@Slf4j
@Service
public class SendMqAction implements BusinessProcess<SendTaskModel> {
    @Autowired
    private SendMqService sendMqService;

    @Value("${PushMatrix.topic.name}")
    private String sendMessageTopic;

//    @Value("${austin.business.tagId.value}")
//    private String tagId;

//    @Value("${austin.mq.pipeline}")
//    private String mqPipeline;

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        List<TaskInfo> taskInfo = sendTaskModel.getTaskInfo();
        try {
            String message = JSON.toJSONString(sendTaskModel.getTaskInfo(), new SerializerFeature[]{SerializerFeature.WriteClassName});
            sendMqService.send(sendMessageTopic, message);
            log.info("消息传入mq成功");
            context.setResponse(BasicResultVO.success(taskInfo.stream().map(v -> SimpleTaskInfo.builder().businessId(v.getBusinessId()).messageId(v.getMessageId()).bizId(v.getBizId()).build()).collect(Collectors.toList())));
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
//            log.error("send {} fail! e:{},params:{}", mqPipeline, Throwables.getStackTraceAsString(e)
//                    , JSON.toJSONString(CollUtil.getFirst(taskInfo.listIterator())));
        }
    }
}
