package org.example.pushMatrix.controller;


import org.example.pushMatrix.domain.MessageParam;
import org.example.pushMatrix.domain.SendRequest;
import org.example.pushMatrix.domain.SendResponse;
import org.example.pushMatrix.enums.BusinessCode;
import org.example.pushMatrix.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/4 13:37
 */
@RestController
public class SendController {
    @Resource
    private SendService sendService;

    @GetMapping("/send")
    public SendResponse sendSmsV2(@RequestBody SendRequest sendRequest) {

        return sendService.send(sendRequest);
    }
}
