package org.example.pushMatrix.serviceimplapi.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.serviceimplapi.domain.SendTaskModel;
import org.example.pushMatrix.common.enums.RespStatusEnum;
import org.example.pushMatrix.common.pipeline.BusinessProcess;
import org.example.pushMatrix.common.pipeline.ProcessContext;
import org.example.pushMatrix.common.vo.BasicResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/4 8:39
 * 将消息发送向mq中去
 */
@Slf4j
@Service
public class SendMqAction implements BusinessProcess {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Value("${PushMatrix.topic.name}")
    private String topicName;//主题名
    @Override
    public void process(ProcessContext context) {
        SendTaskModel sendTaskModel = (SendTaskModel) context.getProcessModel();
        try {
            kafkaTemplate.send(topicName, JSON.toJSONString(sendTaskModel.getTaskInfo(),
                    new SerializerFeature[] {SerializerFeature.WriteClassName}));
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("send kafka fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }
}
