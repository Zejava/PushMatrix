package org.example.pushMatrix.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author 泽
 * @Date 2024/8/12 20:57
 * 消息模板管理的请求参数 进行测试使用
 */
@Data
@Builder
@AllArgsConstructor
public class MessageTemplateParam {
    /**
     * 当前的页码
     */
    private Integer page;
    /**
     * 当前页大小 （展示多少数据）
     */
    private Integer perPage;
    /**
     * 消息模板ID
     */
    private Long id;
    /**
     * 消息接收者(测试发送时使用)
     */
    private String receiver;
    /**
     * 下发消息参数信息
     */
    private String msgContent;

}
