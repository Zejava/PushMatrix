package org.example.pushMatrix.cron.eunms;

/**
 * @Author 泽
 * @Date 2024/8/12 18:06
 * 调度类型
 */
public enum ScheduleTypeEnum {
    NONE,
    /**
     * schedule by cron
     */
    CRON,

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE;

    ScheduleTypeEnum() {
    }
}
