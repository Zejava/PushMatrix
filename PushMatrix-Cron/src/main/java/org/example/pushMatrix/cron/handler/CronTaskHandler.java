package org.example.pushMatrix.cron.handler;

import com.alibaba.fastjson.JSON;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.support.domain.MessageTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/12 18:07
 * 定时任务的处理类
 */
@Service
@Slf4j
public class CronTaskHandler {
    /**
     * 处理所有的 austin 定时任务消息
     */
    @XxlJob("pushMatrix")
    public void execute() {
        log.info("XXL-JOB, Hello World.");
        MessageTemplate messageTemplate = JSON.parseObject(XxlJobHelper.getJobParam(), MessageTemplate.class);
    }
}
