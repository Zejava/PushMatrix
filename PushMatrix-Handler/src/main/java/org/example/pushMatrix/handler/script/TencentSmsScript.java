package org.example.pushMatrix.handler.script;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.common.dto.account.TencentSmsAccount;
import org.example.pushMatrix.handler.domain.SmsParam;
import org.example.pushMatrix.support.domain.SmsRecord;
import org.example.pushMatrix.common.enums.SmsStatus;
import org.example.pushMatrix.support.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author 泽
 * @Date 2024/8/3 20:25
 * 腾讯短信发送的脚本，封装处理基本信息以及账号信息
 */
@Slf4j
@Component("TencentSmsScript")
public class TencentSmsScript implements SmsScript{
    private static final Integer PHONE_NUM = 11;

    @Autowired
    private AccountUtils accountUtils;

    @Override
    public List<SmsRecord> send(SmsParam smsParam) {
        try {
            TencentSmsAccount tencentSmsAccount = Objects.nonNull(smsParam.getSendAccountId()) ? accountUtils.getAccountById(smsParam.getSendAccountId(), TencentSmsAccount.class)
                    : accountUtils.getSmsAccountByScriptName(smsParam.getScriptName(), TencentSmsAccount.class);
            com.tencentcloudapi.sms.v20210111.SmsClient client = init(tencentSmsAccount);
            com.tencentcloudapi.sms.v20210111.models.SendSmsRequest request = assembleSendReq(smsParam, tencentSmsAccount);
            com.tencentcloudapi.sms.v20210111.models.SendSmsResponse response = client.SendSms(request);
            return assembleSendSmsRecord(smsParam, response, tencentSmsAccount);
        } catch (Exception e) {
            log.error("TencentSmsScript#send fail:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(smsParam));
            return new ArrayList<>();
        }
    }


    /**
     * 组装 发送消息的 返回值
     *
     * @param smsParam
     * @param response
     * @param tencentSmsAccount
     * @return
     */
    private List<SmsRecord> assembleSendSmsRecord(SmsParam smsParam, SendSmsResponse response, TencentSmsAccount tencentSmsAccount) {

        List<SmsRecord> smsRecordList = new ArrayList<>();
        if (Objects.isNull(response) || ArrayUtil.isEmpty(response.getSendStatusSet())) {
            return smsRecordList;
        }

        for (SendStatus sendStatus : response.getSendStatusSet()) {

            // 腾讯返回的电话号有前缀，这里取巧直接翻转获取手机号
            String phone = new StringBuilder(new StringBuilder(sendStatus.getPhoneNumber())
                    .reverse().substring(0, PHONE_NUM)).reverse().toString();

            SmsRecord smsRecord = SmsRecord.builder()
                    .sendDate(Integer.valueOf(DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN)))
                    .messageTemplateId(smsParam.getMessageTemplateId())
                    .phone(Long.valueOf(phone))
                    .supplierId(tencentSmsAccount.getSupplierId())
                    .supplierName(tencentSmsAccount.getSupplierName())
                    .msgContent(smsParam.getContent())
                    .seriesId(sendStatus.getSerialNo())
                    .chargingNum(Math.toIntExact(sendStatus.getFee()))
                    .status(SmsStatus.SEND_SUCCESS.getCode())
                    .reportContent(sendStatus.getCode())
                    .created(Math.toIntExact(DateUtil.currentSeconds()))
                    .updated(Math.toIntExact(DateUtil.currentSeconds()))
                    .build();

            smsRecordList.add(smsRecord);
        }
        return smsRecordList;
    }

    /**
     * 组装发送短信参数
     */
    private com.tencentcloudapi.sms.v20210111.models.SendSmsRequest assembleSendReq(SmsParam smsParam, TencentSmsAccount account) {
        com.tencentcloudapi.sms.v20210111.models.SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet1 = smsParam.getPhones().toArray(new String[smsParam.getPhones().size() - 1]);
        req.setPhoneNumberSet(phoneNumberSet1);
        req.setSmsSdkAppId(account.getSmsSdkAppId());
        req.setSignName(account.getSignName());
        req.setTemplateId(account.getTemplateId());
        String[] templateParamSet1 = {smsParam.getContent()};
        req.setTemplateParamSet(templateParamSet1);
        req.setSessionContext(IdUtil.fastSimpleUUID());
        return req;
    }

    /**
     * 初始化 client
     *
     * @param account
     */
    private com.tencentcloudapi.sms.v20210111.SmsClient init(TencentSmsAccount account) {
        Credential cred = new Credential(account.getSecretId(), account.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(account.getUrl());
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(cred, account.getRegion(), clientProfile);
    }
}
