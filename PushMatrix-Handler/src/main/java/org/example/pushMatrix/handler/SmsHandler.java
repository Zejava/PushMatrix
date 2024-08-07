package org.example.pushMatrix.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.example.pushMatrix.dao.SmsRecordDao;
import org.example.pushMatrix.domain.SmsParam;
import org.example.pushMatrix.domain.SmsRecord;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.dto.SmsContentModel;
import org.example.pushMatrix.script.SmsScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/2 22:39
 * 对发送短信的处理
 */
@Component
public class SmsHandler extends Handler{
    @Autowired
    private SmsRecordDao smsRecordDao;//对消息持久化

    @Autowired
    private SmsScript smsScript;
    @Override
    public void handler(TaskInfo taskInfo) {

        SmsParam smsParam = SmsParam.builder()//组装SmsParam的参数
                .phones(taskInfo.getReceiver())
                .content(getSmsContent(taskInfo))
                .messageTemplateId(taskInfo.getMessageTemplateId())
                .supplierId(10)
                .supplierName("腾讯云通知类消息渠道").build();
        List<SmsRecord> recordList = smsScript.send(smsParam);

        if (!CollUtil.isEmpty(recordList)) {
            smsRecordDao.saveAll(recordList);
        }
    }


    /**
     * 如果有输入链接，则把链接拼在文案后
     * PS: 这里可以考虑将链接 转 短链
     * PS: 如果是营销类的短信，需考虑拼接 回TD退订 之类的文案
     */
    private String getSmsContent(TaskInfo taskInfo) {
        SmsContentModel smsContentModel = (SmsContentModel) taskInfo.getContentModel();
        if (StrUtil.isNotBlank(smsContentModel.getUrl())) {
            return smsContentModel.getContent() + " " + smsContentModel.getUrl();
        } else {
            return smsContentModel.getContent();
        }
    }
}
