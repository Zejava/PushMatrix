package org.example.pushMatrix.common.constant;

/**
 * @Author 泽
 * @Date 2024/8/14 19:03
 * 线程池常见的常量配置信息
 */
public class ThreadPoolConstant {
    /**
     * small
     */
    public static final Integer SINGLE_CORE_POOL_SIZE = 1;
    public static final Integer SINGLE_MAX_POOL_SIZE = 1;
    public static final Integer SMALL_KEEP_LIVE_TIME = 10;
    /**
     * medium
     */
    public static final Integer COMMON_CORE_POOL_SIZE = 2;
    public static final Integer COMMON_MAX_POOL_SIZE = 2;
    public static final Integer COMMON_KEEP_LIVE_TIME = 60;
    public static final Integer COMMON_QUEUE_SIZE = 128;
    /**
     * big queue size
     */
    public static final Integer BIG_QUEUE_SIZE = 1024;


    private ThreadPoolConstant() {
    }
}
