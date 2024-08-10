package org.example.pushMatrix.deduplication.service;

import cn.hutool.core.util.StrUtil;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.enums.DeduplicationType;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/8 17:40
 * 频次去重的服务
 */
@Service
public class FrequencyDeduplicationService extends AbstractDeduplicationService {
    public FrequencyDeduplicationService() {
        deduplicationType = DeduplicationType.FREQUENCY.getCode();
    }

    private static final String PREFIX = "FRE";

    /**
     * 业务规则去重 构建key
     * <p>
     * key ： receiver + templateId + sendChannel
     * <p>
     * 一天内一个用户只能收到某个渠道的消息 N 次
     *
     * @param taskInfo
     * @param receiver
     * @return
     */
    @Override
    public String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        return PREFIX + StrUtil.C_UNDERLINE
                + receiver  + StrUtil.C_UNDERLINE
                + taskInfo.getMessageTemplateId() + StrUtil.C_UNDERLINE
                + taskInfo.getSendChannel();
    }

}
