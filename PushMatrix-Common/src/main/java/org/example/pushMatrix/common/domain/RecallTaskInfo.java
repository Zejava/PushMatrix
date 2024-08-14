package org.example.pushMatrix.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/14 21:34
 * 撤回任务消息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecallTaskInfo {
    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 需要撤回的消息ids
     * （有传入消息ids时，优先撤回dis）
     */
    private List<String> recallMessageId;

    /**
     * 发送账号
     */
    private Integer sendAccount;

    /**
     * 发送渠道
     */
    private Integer sendChannel;
}
