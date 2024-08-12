package org.example.pushMatrix.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pushMatrix.common.dto.ContentModel;

import java.util.Set;

/**
 * @Author 泽
 * @Date 2024/8/2 22:25
 * 发送的任务信息
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
     * 业务方Id 数据追踪使用
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
     * 但是不同的渠道要发送的内容不一样 比如发push会有img
     * 所以会有ContentModel用来容纳不同的消息体
     */
    private ContentModel contentModel;
    /**
     * 发送消息的账号（邮件下可有多个发送账号、短信可有多个发送账号..）
     */
    private Integer sendAccount;
}
