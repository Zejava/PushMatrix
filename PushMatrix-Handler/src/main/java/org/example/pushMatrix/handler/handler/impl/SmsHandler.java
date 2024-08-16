package org.example.pushMatrix.handler.handler.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.common.domain.RecallTaskInfo;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.dto.SmsContentModel;
import org.example.pushMatrix.common.enums.ChannelType;
import org.example.pushMatrix.handler.domain.SmsParam;
import org.example.pushMatrix.handler.handler.BaseHandler;
import org.example.pushMatrix.handler.script.SmsScript;
import org.example.pushMatrix.support.dao.SmsRecordDao;
import org.example.pushMatrix.support.domain.SmsRecord;
import org.example.pushMatrix.support.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/16 1:57
 * 短信消息发送
 */
@Service
@Slf4j
public class SmsHandler extends BaseHandler {
    /**
     * 流量自动分配策略
     */
    private static final Integer AUTO_FLOW_RULE = 0;
    private static final String FLOW_KEY = "msgTypeSmsConfig";
    private static final String FLOW_KEY_PREFIX = "message_type_";
    /**
     * 安全随机数，重用性能与随机数质量更高
     */
    private static final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private SmsRecordDao smsRecordDao;
//    @Autowired
//    private ConfigService config;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private SmsScript SmsScript;

    public SmsHandler() {
        channelCode = ChannelType.SMS.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        //1.组装发送消息的参数
        SmsParam smsParam = SmsParam.builder()
                .phones(taskInfo.getReceiver())
                .content(getSmsContent(taskInfo))
                .messageTemplateId(taskInfo.getMessageTemplateId())
                .sendAccountId(taskInfo.getSendAccount())
                .sendAccount(taskInfo.getSendAccount())
                .build();
        try {

            List<SmsRecord> send = SmsScript.send(smsParam);
            if (CollUtil.isNotEmpty(send)) {
                    smsRecordDao.saveAll(send);
                    return true;
                }
        } catch (Exception e) {
            log.error("SmsHandler#handler fail:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(smsParam));
        }
        return false;
    }





    /**
     * 如果有输入链接，则把链接拼在文案后
     * <p>
     * PS: 这里可以考虑将链接 转 短链
     * PS: 如果是营销类的短信，需考虑拼接 回TD退订 之类的文案
     */
    private String getSmsContent(TaskInfo taskInfo) {
        SmsContentModel smsContentModel = (SmsContentModel) taskInfo.getContentModel();
        if (CharSequenceUtil.isNotBlank(smsContentModel.getUrl())) {
            return smsContentModel.getContent() + CharSequenceUtil.SPACE + smsContentModel.getUrl();
        } else {
            return smsContentModel.getContent();
        }
    }

    /**
     * 短信不支持撤回
     * 腾讯云文档 eg：https://cloud.tencent.com/document/product/382/52077
     * @param recallTaskInfo
     */
    @Override
    public void recall(RecallTaskInfo recallTaskInfo) {


    }
}
