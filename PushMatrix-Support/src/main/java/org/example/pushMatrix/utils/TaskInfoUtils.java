package org.example.pushMatrix.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @Author 泽
 * @Date 2024/8/4 8:14
 * 生成消息推送的URL工具类
 */
public class TaskInfoUtils {
    private static int TYPE_FLAG = 1000000;

    /**
     * 生成BusinessId
     * 模板类型+模板ID+当天日期
     * (固定16位)
     */
    public static Long generateBusinessId(Long templateId, Integer templateType) {
        //主要以时间戳作为id生成主题
        Integer today = Integer.valueOf(DateUtil.format(new Date(), "yyyyMMdd"));
        return Long.valueOf(String.format("%d%s", templateType * TYPE_FLAG + templateId, today));
    }
}
