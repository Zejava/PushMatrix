package org.example.pushMatrix.pipeline;

/**
 * @Author 泽
 * @Date 2024/8/3 22:59
 * 业务执行器 用来处理业务流程的接口
 */
public interface BusinessProcess {
    /**
     * 真正处理业务逻辑
     * @param context
     */
    void process(ProcessContext context);
}
