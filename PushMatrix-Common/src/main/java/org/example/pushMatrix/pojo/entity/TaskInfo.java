package org.example.pushMatrix.pojo.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @Author 泽
 * @Date 2024/8/2 22:25
 */
@Data
@Builder
public class TaskInfo {
    /**
     * 消息模板Id
     */
    private Long messageTemplateId;
    /**
     * 业务方Id
     */
    private Long businessId;
    /**
     * 接收者，一个或多个且不重复，使用set集合
     */
    private Set<String> receiver;
    /**
     * 接收消息方的Id类型（手机号，邮箱等等）
     */
    private Integer idType;
    /**
     * 消息的发送渠道
     */
    private Integer sendChannel;
    /**
     * 消息模板的类型
     */
    private Integer templateType;
    /**
     * 消息的类型
     */
    private Integer msgType;
    /**
     * 发送文案内容，这里后续需要优化，消息内容不一定是String类型 todo
     */
    private String content;
    /**
     * 发送消息的账号（邮件下可有多个发送账号、短信可有多个发送账号..）
     */
    private Integer sendAccount;
    /**
     * 消息去重时间 单位小时
     */
    private Integer deduplicationTime;

    /**
     * 是否夜间屏蔽
     * 0:不屏蔽
     * 1：屏蔽
     */
    private Integer isNightShield;
}
