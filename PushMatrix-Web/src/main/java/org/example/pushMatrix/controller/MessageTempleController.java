package org.example.pushMatrix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.support.domain.MessageTemplate;
import org.example.pushMatrix.service.MessageTemplateService;
import org.example.pushMatrix.serviceapi.service.SendService;
import org.example.pushMatrix.common.vo.BasicResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 泽
 * @Date 2024/8/12 21:03
 */
@RestController
@Slf4j
@RequestMapping("/messageTemplate")
@Tag(name = "发送消息")
public class MessageTempleController {
//    @Autowired
//    private MessageTemplateService messageTemplateService;
//
//    @Autowired
//    private SendService sendService;
//    @PostMapping("/save")
//    @Operation(summary = "保存数据")
//    public BasicResultVO saveOrUpdate(@RequestBody MessageTemplate messageTemplate){
//        messageTemplateService.saveOrUpdate(messageTemplate);
//        return BasicResultVO.success();
//    }

}
