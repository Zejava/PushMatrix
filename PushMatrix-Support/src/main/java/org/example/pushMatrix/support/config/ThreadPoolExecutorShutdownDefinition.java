package org.example.pushMatrix.support.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 优雅关闭线程池
 * @Author 泽
 * @Date 2024/9/1 13:40
 */
@Component
@Slf4j
public class ThreadPoolExecutorShutdownDefinition implements ApplicationListener<ContextClosedEvent> {
    /**
     * 线程中任务收到关闭通知后，在多少秒内必须强制销毁，为后面的任务留出时间
     */
    private static final long AWAIT_TERMINATION = 20;
    /**
     * awaitTermination的单位
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private final List<ExecutorService> POOLS = Collections.synchronizedList(new ArrayList<>(12));

    public void registryExecutor(ExecutorService executor) {
        POOLS.add(executor);
    }//将ExecutorService实例添加到POOLS列表中。这个列表是线程安全的，

    /**
     * 参考{@link org.springframework.scheduling.concurrent.ExecutorConfigurationSupport#shutdown()}
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // 使用了Collections.synchronizedList来包装一个ArrayList，确保在多线程环境下对列表的操作是安全的。
        log.info("容器关闭前处理线程池优雅关闭开始, 当前要处理的线程池数量为: {} >>>>>>>>>>>>>>>>", POOLS.size());
        if (CollectionUtils.isEmpty(POOLS)) {
            return;
        }
        for (ExecutorService pool : POOLS) {//遍历POOLS列表中的每一个线程池，并尝试优雅地关闭它们
            pool.shutdown();
            try {
                if (!pool.awaitTermination(AWAIT_TERMINATION, TIME_UNIT)) {
                    log.warn("Timed out while waiting for executor [{}] to terminate", pool);
                }
            } catch (InterruptedException ex) {
                log.warn("Timed out while waiting for executor [{}] to terminate", pool);
                Thread.currentThread().interrupt();
            }
        }
    }
}
