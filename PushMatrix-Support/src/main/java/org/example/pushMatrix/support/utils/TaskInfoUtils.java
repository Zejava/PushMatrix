package org.example.pushMatrix.support.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import org.example.pushMatrix.common.constant.FunctionConstant;

import java.util.Date;

/**
 * @Author 泽
 * @Date 2024/8/4 8:14
 * 生成消息推送的URL工具类
 */
public class TaskInfoUtils {
    private static int TYPE_FLAG = 1000000;
    private static final String CODE = "track_code_bid";
    private TaskInfoUtils() {
    }

    /**
     * 生成任务唯一Id
     *
     * @return
     */
    public static String generateMessageId() {
        return IdUtil.nanoId();
    }



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


    public static Long getMessageTemplateIdFromBusinessId(Long businessId) {
        return Long.valueOf(String.valueOf(businessId).substring(1, 8));
    }

    /**
     * 从businessId切割出日期
     */
    public static Long getDateFromBusinessId(Long businessId) {
        return Long.valueOf(String.valueOf(businessId).substring(8));
    }


    /**
     * 对url添加平台参数（用于追踪数据)
     */
    public static String generateUrl(String url, Long templateId, Integer templateType) {
        url = url.trim();
        Long businessId = generateBusinessId(templateId, templateType);
        if (url.indexOf(FunctionConstant.QM) == -1) {
            return url + FunctionConstant.QM_STRING + CODE + FunctionConstant.EQUAL_STRING + businessId;
        } else {
            return url + FunctionConstant.AND_STRING + CODE + FunctionConstant.EQUAL_STRING + businessId;
        }
    }

}
