package org.example.pushMatrix.config;

/**
 * @Author 泽
 * @Date 2024/8/6 17:37
 * 线程池的配置信息
 */

import cn.hutool.core.thread.ExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync//开启对异步方法的支持
public class ThreadPoolConfig {
    @Bean("smsThreadPool")
    public static ExecutorService getSmsThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = ExecutorBuilder.create()
                .setCorePoolSize(4)//线程池的核心线程数量
                .setMaxPoolSize(4)//线程池的最大线程数
                .setKeepAliveTime(60)//当线程数大于核心线程数时，多余的空闲线程存活的最长时间
                .setWorkQueue(new LinkedBlockingQueue<>(1000))
                .setHandler((r, executor) -> {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                    }
                })
                .build();

        return threadPoolExecutor;
    }

    @Bean("emailThreadPool")
    public static ExecutorService getEmailThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = ExecutorBuilder.create()
                .setCorePoolSize(2)
                .setMaxPoolSize(2)
                .setKeepAliveTime(60)
                .setWorkQueue(new LinkedBlockingQueue<>(1000))
                .setHandler((r, executor) -> {//设置饱和策略
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                    }
                })
                .build();
        return threadPoolExecutor;
    }
}
