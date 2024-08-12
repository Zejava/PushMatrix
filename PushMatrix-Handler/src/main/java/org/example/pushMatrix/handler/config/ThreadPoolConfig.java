package org.example.pushMatrix.handler.config;

/**
 * @Author 泽
 * @Date 2024/8/6 17:37
 * 线程池配置
 */

import cn.hutool.core.thread.ExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync//开启对异步方法的支持
public class ThreadPoolConfig {
    /**
     * 利用CallerRunsPolicy策略，阻塞队列满了，也不丢弃任务
     * @param coreSize
     * @param maxSize
     * @param queueSize
     * @return
     */
    public static ExecutorService getThreadPool(Integer coreSize, Integer maxSize, Integer queueSize) {
        ThreadPoolExecutor threadPoolExecutor = ExecutorBuilder.create()
                .setCorePoolSize(coreSize)//线程池的核心线程数量
                .setMaxPoolSize(maxSize)//线程池的最大线程数
                .setKeepAliveTime(60, TimeUnit.SECONDS)//当线程数大于核心线程数时，多余的空闲线程存活的最长时间
                .setWorkQueue(new LinkedBlockingQueue<>(queueSize))
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();

        return threadPoolExecutor;
    }
}
