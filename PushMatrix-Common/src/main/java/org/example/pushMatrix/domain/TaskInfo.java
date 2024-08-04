package org.example.pushMatrix.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pushMatrix.dto.ContentModel;

import java.util.Set;

/**
 * @Author 泽
 * @Date 2024/8/2 22:25
 * 消息任务类，管理消息模板的
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
     * 发送文案模型
     * message_template表存储的content是JSON格式(所有内容都会塞进去)
     * 但是不同的渠道要发送的内容不一样 比如发push会有img
     * 所以会有ContentModel用来容纳不同的消息体
     */
    private ContentModel contentModel;
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
