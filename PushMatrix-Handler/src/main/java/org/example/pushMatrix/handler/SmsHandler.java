package org.example.pushMatrix.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.dao.SmsRecordDao;
import org.example.pushMatrix.domain.SmsParam;
import org.example.pushMatrix.domain.SmsRecord;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.dto.SmsContentModel;
import org.example.pushMatrix.script.SmsScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/2 22:39
 * 对发送短信的处理
 */
@Component
@Slf4j
public class SmsHandler extends Handler{

    @Autowired
    private SmsRecordDao smsRecordDao;//对消息持久化

    @Autowired
    private SmsScript smsScript;
    @Override
    public void handler(TaskInfo taskInfo) throws TencentCloudSDKException {//可能存在消息已发送后服务崩溃，那么消息数据没存入数据库的情况 这里就体现数据埋点的作用了
        //1.组装SmsParam的参数
        SmsParam smsParam = SmsParam.builder()
                .phones(taskInfo.getReceiver())
                .content(getSmsContent(taskInfo))
                .messageTemplateId(taskInfo.getMessageTemplateId())
                .sendAccount(taskInfo.getSendAccount()).build();
        //2.实现消息的发送
        List<SmsRecord> recordList = smsScript.send(smsParam);
        //3.存入数据库
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
        //hutool包的StrUtil，如果是null，““，或者仅包含空白字符，则返回false
        if (StrUtil.isNotBlank(smsContentModel.getUrl())) {
            // 发送包含URL的短信
            return smsContentModel.getContent() + " " + smsContentModel.getUrl();
        } else {
            return smsContentModel.getContent();
        }
    }
}
