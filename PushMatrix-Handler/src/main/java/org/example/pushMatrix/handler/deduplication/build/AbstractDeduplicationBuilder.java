package org.example.pushMatrix.handler.deduplication.build;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.PostConstruct;
import org.example.pushMatrix.handler.deduplication.DeduplicationHolder;
import org.example.pushMatrix.handler.deduplication.DeduplicationParam;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author 泽
 * @Date 2024/8/10 17:40
 */
public abstract class AbstractDeduplicationBuilder implements Builder {
    protected Integer deduplicationType;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @PostConstruct
    public void init() {
        deduplicationHolder.putBuilder(deduplicationType, this);
    }

    public DeduplicationParam getParamsFromConfig(Integer key, String duplicationConfig, TaskInfo taskInfo) {
        JSONObject object = JSONObject.parseObject(duplicationConfig);
        if (object == null) {
            return null;
        }
        DeduplicationParam deduplicationParam = JSONObject.parseObject(object.getString(CONFIG_PRE + key), DeduplicationParam.class);
        if (deduplicationParam == null) {
            return null;
        }
        deduplicationParam.setTaskInfo(taskInfo);
        return deduplicationParam;
    }
}
