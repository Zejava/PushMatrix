package org.example.pushMatrix.handler.deduplication.build;

import cn.hutool.core.date.DateUtil;
import org.example.pushMatrix.handler.domain.DeduplicationParam;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.enums.AnchorState;
import org.example.pushMatrix.common.enums.DeduplicationType;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author 泽
 * @Date 2024/8/10 17:51
 * 根据时间进行去重的构建器
 */
@Service
public class FrequencyDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder{
    public FrequencyDeduplicationBuilder() {
        deduplicationType = DeduplicationType.FREQUENCY.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (deduplicationParam == null) {
            return null;
        }
        deduplicationParam.setDeduplicationTime((DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000);
        deduplicationParam.setAnchorState(AnchorState.RULE_DEDUPLICATION);
        return deduplicationParam;
    }
}
