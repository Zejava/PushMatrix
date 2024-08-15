package org.example.pushMatrix.handler.action;
import org.example.pushMatrix.support.service.ConfigService;
import cn.hutool.core.collection.CollUtil;
import org.example.pushMatrix.common.constant.FunctionConstant;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.enums.DeduplicationType;
import org.example.pushMatrix.common.enums.EnumUtil;
import org.example.pushMatrix.common.pipeline.BusinessProcess;
import org.example.pushMatrix.common.pipeline.ProcessContext;
import org.example.pushMatrix.handler.deduplication.DeduplicationHolder;
import org.example.pushMatrix.handler.deduplication.DeduplicationParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author 泽
 * @Date 2024/8/14 21:58
 * 去重服务
 *1. 根据相同内容N分钟去重（SlideWindowLimitService）
 *2. 相同的渠道一天内频次去重（SimpleLimitService）
 */
@Service
public class DeduplicationAction implements BusinessProcess<TaskInfo> {
    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

    @Autowired
    private ConfigService config;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();

        // 配置样例{"deduplication_10":{"num":1,"time":300},"deduplication_20":{"num":5}}
        String deduplicationConfig = config.getProperty(DEDUPLICATION_RULE_KEY, FunctionConstant.EMPTY_JSON_OBJECT);

        // 去重
        List<Integer> deduplicationList = EnumUtil.getCodeList(DeduplicationType.class);//获得去重类型code
        for (Integer deduplicationType : deduplicationList) {//遍历 每个去重类型选择对应构造器
            DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationType).build(deduplicationConfig, taskInfo);
            if (Objects.nonNull(deduplicationParam)) {//选择对应处理器进行去重处理
                deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
            }
        }

        if (CollUtil.isEmpty(taskInfo.getReceiver())) {//没有消息接收者，责任链中断
            context.setNeedBreak(true);
        }
    }

}
