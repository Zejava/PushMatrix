package org.example.pushMatrix.deduplication.service;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.constant.PushMatrixConstant;
import org.example.pushMatrix.domain.AnchorInfo;
import org.example.pushMatrix.domain.DeduplicationParam;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.deduplication.DeduplicationHolder;
import org.example.pushMatrix.utils.LogUtils;
import org.example.pushMatrix.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @Author 泽
 * @Date 2024/8/8 17:16
 * 去重服务的抽象类
 */
@Slf4j
public abstract class AbstractDeduplicationService implements DeduplicationService{
    protected Integer deduplicationType;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @PostConstruct
    private void init() {
        deduplicationHolder.putService(deduplicationType, this);
    }

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void deduplication(DeduplicationParam param) {
        TaskInfo taskInfo = param.getTaskInfo();
        Set<String> filterReceiver = new HashSet<>(taskInfo.getReceiver().size());

        // 获取redis记录
        Set<String> readyPutRedisReceiver = new HashSet<>(taskInfo.getReceiver().size());
        List<String> keys = deduplicationAllKey(taskInfo);
        Map<String, String> inRedisValue = redisUtils.mGet(keys);

        for (String receiver : taskInfo.getReceiver()) {
            String key = deduplicationSingleKey(taskInfo, receiver);
            String value = inRedisValue.get(key);

            // 符合条件的用户
            if (value != null && Integer.valueOf(value) >= param.getCountNum()) {
                filterReceiver.add(receiver);
            } else {
                readyPutRedisReceiver.add(receiver);
            }
        }

        // 不符合条件的用户：需要更新Redis(无记录添加，有记录则累加次数)
        putInRedis(readyPutRedisReceiver, inRedisValue, param);

        // 剔除符合去重条件的用户
        if (CollUtil.isNotEmpty(filterReceiver)) {
            taskInfo.getReceiver().removeAll(filterReceiver);
            LogUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(filterReceiver).state(param.getAnchorState().getCode()).build());
        }
    }


    /**
     * 构建去重的Key
     *
     * @param taskInfo
     * @param receiver
     * @return
     */
    protected abstract String deduplicationSingleKey(TaskInfo taskInfo, String receiver);


    /**
     * 存入redis 实现去重
     *
     * @param readyPutRedisReceiver
     */
    private void putInRedis(Set<String> readyPutRedisReceiver,
                            Map<String, String> inRedisValue, DeduplicationParam param) {
        Map<String, String> keyValues = new HashMap<>(readyPutRedisReceiver.size());
        for (String receiver : readyPutRedisReceiver) {
            String key = deduplicationSingleKey(param.getTaskInfo(), receiver);
            if (inRedisValue.get(key) != null) {
                keyValues.put(key, String.valueOf(Integer.valueOf(inRedisValue.get(key)) + 1));
            } else {
                keyValues.put(key, String.valueOf(PushMatrixConstant.TRUE));
            }
        }
        if (CollUtil.isNotEmpty(keyValues)) {
            redisUtils.pipelineSetEX(keyValues, param.getDeduplicationTime());
        }
    }

    /**
     * 获取得到当前消息模板所有的去重Key
     *
     * @param taskInfo
     * @return
     */
    private List<String> deduplicationAllKey(TaskInfo taskInfo) {
        List<String> result = new ArrayList<>(taskInfo.getReceiver().size());
        for (String receiver : taskInfo.getReceiver()) {
            String key = deduplicationSingleKey(taskInfo, receiver);
            result.add(key);
        }
        return result;
    }


}
