package org.example.pushMatrix.eunms;

/**
 * @Author 泽
 * @Date 2024/8/12 18:04
 * 执行阻塞队列
 */
public enum ExecutorBlockStrategyEnum {
    /**
     * 单机串行
     */
    SERIAL_EXECUTION,

    /**
     * 丢弃后续调度
     */
    DISCARD_LATER,

    /**
     * 覆盖之前调度
     */
    COVER_EARLY;

    ExecutorBlockStrategyEnum() {

    }
}
