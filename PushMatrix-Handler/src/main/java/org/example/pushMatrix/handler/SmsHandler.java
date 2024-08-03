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
 */
@Component
public class SmsHandler implements Handler{
    @Autowired
    private SmsRecordDao smsRecordDao;//对消息持久化

    @Autowired
    private SmsScript smsScript;
    @Override
    public boolean doHandler(TaskInfo taskInfo) {
        SmsContentModel smsContentModel = (SmsContentModel) taskInfo.getContentModel();

        String resultContent;
        if (StrUtil.isNotBlank(smsContentModel.getUrl())) {
            resultContent = smsContentModel.getContent() + " " + smsContentModel.getUrl();
        } else {
            resultContent = smsContentModel.getContent();
        }

        SmsParam smsParam = SmsParam.builder()
                .phones(taskInfo.getReceiver())
                .content(resultContent)
                .messageTemplateId(taskInfo.getMessageTemplateId())
                .supplierId(10)
                .supplierName("腾讯云通知类消息渠道").build();
        List<SmsRecord> recordList = smsScript.send(smsParam);

        if (CollUtil.isEmpty(recordList)) {
            return false;
        }

        smsRecordDao.saveAll(recordList);
        return true;
    }

}
