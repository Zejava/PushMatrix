package org.example.pushMatrix.cron.handler;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.support.config.SupportThreadPoolConfig;
import org.example.pushMatrix.support.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @Author 泽
 * @Date 2024/8/14 18:57
 * 用来处理夜间屏蔽的消息
 * 用途：已经深夜，消息不希望在夜间进行发送，于是设计次日早上进行消息推送
 */
@Service
@Slf4j
public class NightShieldLazyPendingHandler {
    private static final String NIGHT_SHIELD_BUT_NEXT_DAY_SEND_KEY = "night_shield_send";//存储需要在次日发送的任务信息。
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Value("${PushMatrix.topic.name}")
    private String topicName;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 处理 夜间屏蔽(次日早上9点发送的任务)
     */
    @XxlJob("nightShieldLazyJob")
    public void execute() {
        log.info("NightShieldLazyPendingHandler#execute!");
        SupportThreadPoolConfig.getPendingSingleThreadPool().execute(() -> {//将任务提交到一个单线程的线程池中执行。确保任务以串行的方式执行，避免并发问题。
            while (redisUtils.lLen(NIGHT_SHIELD_BUT_NEXT_DAY_SEND_KEY) > 0) {
                String taskInfo = redisUtils.lPop(NIGHT_SHIELD_BUT_NEXT_DAY_SEND_KEY);
                if (CharSequenceUtil.isNotBlank(taskInfo)) {
                    try {
                        kafkaTemplate.send(topicName, JSON.toJSONString(Arrays.asList(JSON.parseObject(taskInfo, TaskInfo.class))
                                , new SerializerFeature[]{SerializerFeature.WriteClassName}));
                    } catch (Exception e) {
                        log.error("nightShieldLazyJob send kafka fail! e:{},params:{}", Throwables.getStackTraceAsString(e), taskInfo);
                    }
                }
            }
        });
    }
}
