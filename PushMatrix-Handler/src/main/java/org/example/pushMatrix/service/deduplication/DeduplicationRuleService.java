package org.example.pushMatrix.service.deduplication;

import cn.hutool.core.date.DateUtil;
import org.example.pushMatrix.domain.DeduplicationParam;
import org.example.pushMatrix.domain.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author 泽
 * @Date 2024/8/8 17:39
 * 去重服务的规则
 */
@Service
public class DeduplicationRuleService {
    @Autowired
    private ContentAbstractDeduplicationService contentDeduplicationService;

    @Autowired
    private FrequencyDeduplicationService frequencyDeduplicationService;
    public void duplication(TaskInfo taskInfo) {

        // 文案去重
        DeduplicationParam contentParams = DeduplicationParam.builder()
                .deduplicationTime(300L).countNum(1).taskInfo(taskInfo)
                .build();
        contentDeduplicationService.deduplication(contentParams);

        // 运营总规则去重(一天内用户收到最多同一个渠道的消息次数)
        Long seconds = (DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000;
        DeduplicationParam businessParams = DeduplicationParam.builder()
                .deduplicationTime(seconds).countNum(5).taskInfo(taskInfo)
                .build();
        frequencyDeduplicationService.deduplication(businessParams);
    }

}
