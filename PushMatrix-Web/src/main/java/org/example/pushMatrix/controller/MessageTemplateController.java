package org.example.pushMatrix.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.pushMatrix.dao.MessageTemplateDao;
import org.example.pushMatrix.domain.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 泽
 * @Date 2024/8/11 15:37
 * 消息模板测试类
 */
@RestController
@RequestMapping("/messageTemplate")
@Tag(name = "发送消息")
public class MessageTemplateController {
    @Autowired
    private MessageTemplateDao messageTemplateDao;

    /**
     * test insert
     */
    @GetMapping("/insert")

    public String insert() {

        MessageTemplate messageTemplate = MessageTemplate.builder()
                .name("test邮件")
                .auditStatus(10)
                .flowId("yyyy")
                .msgStatus(10)
                .idType(50)
                .sendChannel(40)
                .templateType(20)
                .msgType(10)
                .expectPushTime("0")
                .msgContent("{\"content\":\"{$contentValue}\",\"title\":\"{$title}\"}")
                .sendAccount(66)
                .creator("yyyyc")
                .updator("yyyyu")
                .team("yyyt")
                .proposer("yyyy22")
                .auditor("yyyyyyz")
                .isDeleted(0)
                .created(Math.toIntExact(DateUtil.currentSeconds()))
                .updated(Math.toIntExact(DateUtil.currentSeconds()))
                .deduplicationTime(1)
                .isNightShield(0)
                .build();

        MessageTemplate info = messageTemplateDao.save(messageTemplate);

        return JSON.toJSONString(info);

    }

    /**
     * test query
     */
    @GetMapping("/query")
    @Operation(summary  = "获取模板的方法")
    public String query() {
        Iterable<MessageTemplate> all = messageTemplateDao.findAll();
        for (MessageTemplate messageTemplate : all) {
            return JSON.toJSONString(messageTemplate);
        }
        return null;
    }

}
