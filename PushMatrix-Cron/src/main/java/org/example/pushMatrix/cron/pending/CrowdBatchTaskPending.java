package org.example.pushMatrix.cron.pending;

import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.cron.domain.CrowdInfoVo;
import org.example.pushMatrix.support.pending.BatchPendingThread;
import org.example.pushMatrix.support.pending.Pending;
import org.example.pushMatrix.support.pending.PendingParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/13 22:27
 * 批量处理任务信息
 */
@Component
@Slf4j
public class CrowdBatchTaskPending extends Pending<CrowdInfoVo> {
    @Override
    public void initAndStart(PendingParam pendingParam) {
        threadNum = pendingParam.getThreadNum() == null ? threadNum : pendingParam.getThreadNum();
        queue = pendingParam.getQueue();

        for (int i = 0; i < threadNum; ++i) {
            BatchPendingThread<CrowdInfoVo> batchPendingThread = new BatchPendingThread();
            batchPendingThread.setPendingParam(pendingParam);
            batchPendingThread.setName("batchPendingThread-" + i);
            batchPendingThread.start();
        }
    }
    @Override
    public void doHandle(List<CrowdInfoVo> list) {
        log.info("theadName:{},doHandle:{}", Thread.currentThread().getName(), list.size());
    }
}
