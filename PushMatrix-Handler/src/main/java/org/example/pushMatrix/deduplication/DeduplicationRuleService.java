package org.example.pushMatrix.deduplication;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.example.pushMatrix.constant.PushMatrixConstant;
import org.example.pushMatrix.domain.DeduplicationParam;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.enums.DeduplicationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/8 17:39
 * 去重服务的规则 配置去重的频次等
 */
@Service
public class DeduplicationRuleService {
    public static final String DEDUPLICATION_RULE_KEY = "deduplication";
    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @ApolloConfig("pushMatrix")
    private Config config;
    public void duplication(TaskInfo taskInfo) {
            // 配置样例：{"deduplication_10":{"num":1,"time":300},"deduplication_20":{"num":5}}
            String deduplicationConfig = config.getProperty(DEDUPLICATION_RULE_KEY, PushMatrixConstant.APOLLO_DEFAULT_VALUE_JSON_OBJECT);//获取配置

            //1. 获得去重的类型
            List<Integer> deduplicationList = DeduplicationType.getDeduplicationList();
            //2.遍历，根据去重的类型选择合适的构建器和处理器
            for (Integer deduplicationType : deduplicationList) {
                DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationType).build(deduplicationConfig, taskInfo);
                if (deduplicationParam != null) {
                    deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
                }
            }
    }
}
