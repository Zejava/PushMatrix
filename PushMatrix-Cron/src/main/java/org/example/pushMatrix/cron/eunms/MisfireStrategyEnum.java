package org.example.pushMatrix.cron.eunms;

/**
 * @Author 泽
 * @Date 2024/8/12 18:06
 * 调度过期策略
 */
public enum MisfireStrategyEnum {
    /**
     * do nothing
     */
    DO_NOTHING,

    /**
     * fire once now
     */
    FIRE_ONCE_NOW;

    MisfireStrategyEnum() {
    }
}
