package org.example.pushMatrix.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author 泽
 * @Date 2024/8/2 23:05
 * 短信类（回执和发送的记录）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SmsRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 手机号
     */
    private Long phone;

    /**
     * 渠道商Id
     */
    private Integer supplierId;

    /**
     * 渠道商名字
     */
    private String supplierName;

    /**
     * 批次号Id
     */
    private String seriesId;

    /**
     * 计费条数
     */
    private Integer chargingNum;

    /**
     * 回执信息
     */
    private String reportContent;

    /**
     * 短信状态
     */
    private Integer status;

    /**
     * 发送日期
     */
    private Integer sendDate;

    /**
     * 创建时间
     */
    private Integer created;

    /**
     * 更新时间
     */
    private Integer updated;
}
