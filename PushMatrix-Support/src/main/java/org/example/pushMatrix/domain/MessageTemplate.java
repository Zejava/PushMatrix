package org.example.pushMatrix.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @Author 泽
 * @Date 2024/8/2 22:59
 * 消息模板数据前端展示类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MessageTemplate {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 模板标题
     */
    private String name;

    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 定时任务Id(由xxl-job返回)
     */
    private Integer cronTaskId;

    /**
     * 定时发送的人群ID
     * 1. 目前阶段直接填入Id测试
     * 2. 正常是需要通过ID获取文件遍历每个Id
     */
    private String cronCrowdId;

    /**
     * 工单ID（审核模板走工单）
     */
    private String flowId;

    /**
     * 消息状态
     */
    private Integer msgStatus;

    /**
     * 发送的Id类型
     */
    private Integer idType;

    /**
     * 发送渠道
     */
    private Integer sendChannel;

    /**
     * 模板类型
     */
    private Integer templateType;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 推送消息的时间
     * 0：立即发送
     * else：crontab 表达式
     */
    private String expectPushTime;

    /**
     * 消息内容  {$var} 为占位符
     */
    private String msgContent;

    /**
     * 发送账号（邮件下可有多个发送账号、短信可有多个发送账号..）
     */
    private Integer sendAccount;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String updator;

    /**
     * 审核者
     */
    private String auditor;

    /**
     * 业务方团队
     */
    private String team;

    /**
     * 业务方
     */
    private String proposer;

    /**
     * 是否删除
     * 0：已删除
     * 1：删除
     */
    private Integer isDeleted;

    /**
     * 创建时间 单位 s
     */
    private Integer created;

    /**
     * 更新时间 单位s
     */
    private Integer updated;

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
