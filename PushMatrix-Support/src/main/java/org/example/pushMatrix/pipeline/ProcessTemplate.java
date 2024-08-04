package org.example.pushMatrix.pipeline;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/3 23:01
 * 业务执行模板 串联责任链的逻辑部分
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