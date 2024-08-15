package org.example.pushMatrix.handler.pending;

import jakarta.annotation.PostConstruct;
import org.example.pushMatrix.handler.config.ThreadPoolConfig;
import org.example.pushMatrix.handler.utils.GroupIdMappingUtils;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Author 泽
 * @Date 2024/8/7 15:46
 * 存储着消息类型和TaskPending的关系
 */
@Component
public class TaskPendingHolder {
    private Map<String, ExecutorService> taskPendingHolder = new HashMap<>(32);//map映射实现一个消费者组对应一个线程

    /**
     * 获取得到所有的groupId
     */
    private static List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();


    /**
     * 线程池的参数
     */
    private Integer coreSize = 3;
    private Integer maxSize = 3;
    private Integer queueSize = 100;


    /**
     * 给每个渠道，每种消息类型初始化一个线程池
     *
     * TODO 后续用动态线程池实现
     *
     */
    @PostConstruct
    public void init() {
        for (String groupId : groupIds) {
            taskPendingHolder.put(groupId, ThreadPoolConfig.getThreadPool(coreSize, maxSize, queueSize));
        }
    }

    /**
     * 得到对应的线程池
     * @param groupId
     * @return
     */
    public ExecutorService route(String groupId) {
        return taskPendingHolder.get(groupId);
    }//根据消费者组得到对应线程池

}
