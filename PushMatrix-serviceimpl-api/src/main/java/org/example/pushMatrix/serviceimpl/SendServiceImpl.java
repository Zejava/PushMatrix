package org.example.pushMatrix.serviceimpl;

import org.example.pushMatrix.domain.BatchSendRequest;
import org.example.pushMatrix.domain.SendRequest;
import org.example.pushMatrix.domain.SendResponse;
import org.example.pushMatrix.domain.SendTaskModel;
import org.example.pushMatrix.pipeline.ProcessContext;
import org.example.pushMatrix.pipeline.ProcessController;
import org.example.pushMatrix.service.SendService;
import org.example.pushMatrix.vo.BasicResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @Author 泽
 * @Date 2024/8/3 23:14
 * 发送消息给用户的接口
 */

@Service
public class SendServiceImpl implements SendService {
    @Autowired
    private ProcessController processController;
    @Override
    public SendResponse send(SendRequest sendRequest) {

        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Arrays.asList(sendRequest.getMessageParam()))
                .build();

        ProcessContext context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getCode(), process.getResponse().getMsg());
    }

    @Override
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(batchSendRequest.getMessageTemplateId())
                .messageParamList(batchSendRequest.getMessageParamList())
                .build();

        ProcessContext context = ProcessContext.builder()
                .code(batchSendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getCode(), process.getResponse().getMsg());
    }

}
