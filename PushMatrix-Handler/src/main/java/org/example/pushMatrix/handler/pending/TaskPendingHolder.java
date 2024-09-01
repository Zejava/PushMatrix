package org.example.pushMatrix.handler.pending;

import com.dtp.core.thread.DtpExecutor;
import jakarta.annotation.PostConstruct;
import org.example.pushMatrix.handler.config.HandlerThreadPoolConfig;
import org.example.pushMatrix.handler.utils.GroupIdMappingUtils;
import org.example.pushMatrix.support.utils.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Map<String, ExecutorService> holder = new HashMap<>(32);//map映射实现一个消费者组对应一个线程

    /**
     * 获取得到所有的groupId
     */
    private static List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();

    @Autowired
    private ThreadPoolUtils threadPoolUtils;
    @PostConstruct
    public void init() {
        /**
         *
         * 可以通过apollo配置：dynamic-tp-apollo-dtp.yml  动态修改线程池的信息
         */
        for (String groupId : groupIds) {
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            threadPoolUtils.register(executor);

            holder.put(groupId, executor);
        }
    }


    /**
     * 得到对应的线程池
     * @param groupId
     * @return
     */
    public ExecutorService route(String groupId) {
        return holder.get(groupId);
    }//根据消费者组得到对应线程池

}
