package org.example.pushMatrix.common.pipeline;

/**
 * @Author 泽
 * @Date 2024/8/3 22:59
 * 业务执行器 用来处理任务业务流程的接口
 */
public interface BusinessProcess<T extends ProcessModel> {
    /**
     * 真正处理业务逻辑
     * @param context
     */
    void process(ProcessContext<T> context);
}
