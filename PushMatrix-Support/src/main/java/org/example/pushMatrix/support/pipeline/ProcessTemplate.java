package org.example.pushMatrix.support.pipeline;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/3 23:01
 * 业务执行模板 串联责任链的逻辑部分 将责任制的执行流程进行传递转发的类
 */
public class ProcessTemplate {
    private List<BusinessProcess> processList;

    public List<BusinessProcess> getProcessList() {
        return processList;
    }
    public void setProcessList(List<BusinessProcess> processList) {
        this.processList = processList;
    }
}
