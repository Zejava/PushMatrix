package org.example.pushMatrix.support.pending;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.BlockingQueue;

/**
 * @Author 泽
 * @Date 2024/8/13 21:06\
 * pending的一些初始化参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class PendingParam<T>{
    /**
     * 阻塞队列实现类
     */
    private BlockingQueue<T> queue;
    /**
     * batch 群发的人数阈值，达到就转发 必须填
     */
    private Integer numThreshold;

    /**
     * batch 触发的时间阈值，单位毫秒 必须填
     */
    private Long timeThreshold;
    /**
     * pending具体实现对象
     */
    private Pending pending;
    /**
     * 消费线程数【可选】
     */
    protected Integer threadNum;

}
