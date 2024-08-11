package org.example.pushMatrix.script;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.constant.PushMatrixConstant;
import org.example.pushMatrix.domain.SmsParam;
import org.example.pushMatrix.domain.SmsRecord;
import org.example.pushMatrix.domain.TencentSmsParam;
import org.example.pushMatrix.enums.SmsStatus;
import org.example.pushMatrix.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/3 20:25
 * 腾讯短信发送的脚本，封装处理基本信息以及账号信息
 */
@Service
@Slf4j
public class TencentSmsScript implements SmsScript{
    private static final Integer PHONE_NUM = 11;//手机号限制

    private static final String SMS_ACCOUNT_KEY = "smsAccount";
    private static final String PREFIX = "sms_";
    @Autowired
    private AccountUtils accountUtils;

    @Override
    public List<SmsRecord> send(SmsParam smsParam) throws TencentCloudSDKException {
            TencentSmsParam tencentSmsParam = accountUtils.getAccount(smsParam.getSendAccount(), SMS_ACCOUNT_KEY, PREFIX, TencentSmsParam.builder().build());
            SmsClient client = init(tencentSmsParam);

            SendSmsRequest request = assembleReq(smsParam,tencentSmsParam);

            SendSmsResponse response = client.SendSms(request);

            return assembleSmsRecord(smsParam,response,tencentSmsParam);



    }

    private List<SmsRecord> assembleSmsRecord(SmsParam smsParam, SendSmsResponse response,TencentSmsParam tencentSmsParam) {
        if (response == null || ArrayUtil.isEmpty(response.getSendStatusSet())) {
            return null;
        }

        List<SmsRecord> smsRecordList = new ArrayList<>();

        for (SendStatus sendStatus : response.getSendStatusSet()) {
            String phone = new StringBuilder(new StringBuilder(sendStatus.getPhoneNumber())
                    .reverse().substring(0, PHONE_NUM)).reverse().toString();

            SmsRecord smsRecord = SmsRecord.builder()
                    .sendDate(Integer.valueOf(DateUtil.format(new Date(), PushMatrixConstant.yyyyMMDD)))
                    .messageTemplateId(smsParam.getMessageTemplateId())
                    .phone(Long.valueOf(phone))
                    .supplierId(tencentSmsParam.getSupplierId())
                    .supplierName(tencentSmsParam.getSupplierName())
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
     * 组装发送短信参数,电话号码，短信签名等等
     */
    private SendSmsRequest assembleReq(SmsParam smsParam,TencentSmsParam account) {
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet1 = smsParam.getPhones().toArray(new String[smsParam.getPhones().size() - 1]);
        req.setPhoneNumberSet(phoneNumberSet1);
        req.setSmsSdkAppid(account.getSmsSdkAppId());
        req.setSign(account.getSignName());
        req.setTemplateID(account.getTemplateId());
        String[] templateParamSet1 = {smsParam.getContent()};
        req.setTemplateParamSet(templateParamSet1);
        req.setSessionContext(IdUtil.fastSimpleUUID());
        return req;
    }

    /**
     * 初始化 client
     */
    private SmsClient init(TencentSmsParam account) {
        Credential cred = new Credential(account.getSecretId(), account.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(account.getUrl());
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(cred, account.getRegion(), clientProfile);
        return client;
    }
}
