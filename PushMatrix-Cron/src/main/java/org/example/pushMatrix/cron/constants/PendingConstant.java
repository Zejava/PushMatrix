package org.example.pushMatrix.cron.constants;

/**
 * @Author 泽
 * @Date 2024/8/13 22:09
 * 缓冲pending 常量
 */
public class PendingConstant {
    /**
     * 阻塞队列元素存储量
     */
    public static final Integer QUEUE_SIZE = 100;

    /**
     * 触发执行的数量阈值
     */
    public static final Integer NUM_THRESHOLD = 100;

    /**
     * batch 触发执行的时间阈值，单位毫秒【必填】
     */
    public static final Long TIME_THRESHOLD = 1000L;

    /**
     * 消费线程数
     */
    public static final Integer THREAD_NUM = 2;
}
