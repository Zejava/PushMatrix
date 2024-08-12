package org.example.pushMatrix.handler.deduplication.build;

import org.example.pushMatrix.handler.domain.DeduplicationParam;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.enums.AnchorState;
import org.example.pushMatrix.common.enums.DeduplicationType;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/10 17:50
 * 根据一段时间内消息的次数作为约束进行去重的构建器
 */
@Service
public class ContentDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder{
    //初始化，获得去重类型
    public ContentDeduplicationBuilder() {
        deduplicationType = DeduplicationType.CONTENT.getCode();
    }

    /**
     * 构建去重的相关参数
     * @param deduplication
     * @param taskInfo
     * @return
     */

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (deduplication == null) {
            return null;
        }
        deduplicationParam.setAnchorState(AnchorState.CONTENT_DEDUPLICATION);
        return deduplicationParam;

    }
}
