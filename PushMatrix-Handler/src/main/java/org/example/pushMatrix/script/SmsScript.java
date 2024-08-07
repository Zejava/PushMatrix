package org.example.pushMatrix.script;

import org.example.pushMatrix.domain.SmsParam;
import org.example.pushMatrix.domain.SmsRecord;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/3 17:33
 * 对短信进行处理的脚本接口
 */
public interface SmsScript {
    /**
     * 发送短信的方法
     * @param smsParam 发送短信参数
     * @return 渠道商接口返回值
     */
    List<SmsRecord> send(SmsParam smsParam);
}
