package org.example.pushMatrix.controller;

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
public class SendController {
    @Value("${test:默认值}")
    private String a;
    @Resource
    private SendService sendService;

    @PostMapping("/send")
    public SendResponse sendSmsV2(@RequestBody SendRequest sendRequest) {
        return sendService.send(sendRequest);
    }
    @GetMapping("/s")
    public String s(){
        return  a;
    }
}
