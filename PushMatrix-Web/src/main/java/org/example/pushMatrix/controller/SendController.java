package org.example.pushMatrix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.pushMatrix.serviceapi.domain.SendRequest;
import org.example.pushMatrix.serviceapi.domain.SendResponse;
import org.example.pushMatrix.serviceapi.service.SendService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author 泽
 * @Date 2024/8/4 13:37
 */
@RestController
@Tag(name = "发送消息")
public class SendController {
    @Resource
    private SendService sendService;

    /**
     * 单个文案下发相同的人
     *
     * @param sendRequest
     * @return
     */
    @Operation(summary = "消息下发接口",description = "多渠道多类型下发消息，目前支持邮件和短信，类型支持：验证码、通知类、营销类。")
    @PostMapping("/send")
    public SendResponse send(@RequestBody SendRequest sendRequest) {
        return sendService.send(sendRequest);
    }

}
