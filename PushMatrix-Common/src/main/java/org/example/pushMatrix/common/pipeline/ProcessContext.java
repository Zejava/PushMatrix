package org.example.pushMatrix.common.pipeline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.pushMatrix.common.vo.BasicResultVO;

import java.io.Serializable;

/**
 * @Author 泽
 * @Date 2024/8/3 23:00
 * 责任链上下文的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ProcessContext <T extends ProcessModel> implements Serializable {
    /**
     * 标识责任链的code
     */
    private String code;

    /**
     * 存储责任链上下文数据的模型
     */
    private T processModel;

    /**
     * 责任链中断的标识
     */
    private Boolean needBreak;

    /**
     * 流程处理的结果
     */
    BasicResultVO response;
}
