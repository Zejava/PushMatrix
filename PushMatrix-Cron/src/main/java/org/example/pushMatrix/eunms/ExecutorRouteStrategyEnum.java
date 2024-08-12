package org.example.pushMatrix.eunms;

/**
 * @Author 泽
 * @Date 2024/8/12 18:05
 * 路由策略
 */
public enum ExecutorRouteStrategyEnum {
    FIRST,
    LAST,
    ROUND,
    RANDOM,
    CONSISTENT_HASH,
    LEAST_FREQUENTLY_USED,
    LEAST_RECENTLY_USED,
    FAILOVER,
    BUSYOVER,
    SHARDING_BROADCAST;

    ExecutorRouteStrategyEnum() {
    }
}
