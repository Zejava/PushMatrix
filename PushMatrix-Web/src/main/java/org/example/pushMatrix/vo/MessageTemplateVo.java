package org.example.pushMatrix.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pushMatrix.support.domain.MessageTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/12 21:01
 * 消息模板的Vo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplateVo{
    /**
     * 返回List列表
     */
    private List<MessageTemplate> messageTemplates;

    /**
     * 总条数
     */
    private Long count;
}
