package org.example.pushMatrix.cron.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.cron.constants.PendingConstant;
import org.example.pushMatrix.cron.domain.CrowdInfoVo;
import org.example.pushMatrix.cron.pending.CrowdBatchTaskPending;
import org.example.pushMatrix.cron.service.TaskHandler;
import org.example.pushMatrix.cron.utils.ReadFileUtils;
import org.example.pushMatrix.support.dao.MessageTemplateDao;
import org.example.pushMatrix.support.domain.MessageTemplate;
import org.example.pushMatrix.support.pending.PendingParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author 泽
 * @Date 2024/8/13 22:25
 *
 */
@Service
@Slf4j
public class TaskHandlerImpl implements TaskHandler {
    @Autowired
    private MessageTemplateDao messageTemplateDao;
    @Autowired
    private CrowdBatchTaskPending crowdBatchTaskPending;
    @Override
    @Async
    public void handle(Long messageTemplateId) {
        log.info("start:{}", Thread.currentThread().getName());
        MessageTemplate messageTemplate = messageTemplateDao.findById(messageTemplateId).get();
        if (messageTemplate == null || StrUtil.isBlank(messageTemplate.getCronCrowdPath())) {
            log.error("TaskHandler#handle crowdPath empty! messageTemplateId:{}", messageTemplateId);
            return;
        }

        // 初始化pending的信息
        PendingParam<CrowdInfoVo> pendingParam = new PendingParam<>();
        pendingParam.setNumThreshold(PendingConstant.NUM_THRESHOLD)
                .setQueue(new LinkedBlockingQueue(PendingConstant.QUEUE_SIZE))
                .setTimeThreshold(PendingConstant.TIME_THRESHOLD)
                .setThreadNum(PendingConstant.THREAD_NUM)
                .setPending(crowdBatchTaskPending);
        crowdBatchTaskPending.initAndStart(pendingParam);

        // 读取文件得到每一行记录给到队列做batch处理
        ReadFileUtils.getCsvRow(messageTemplate.getCronCrowdPath(), row -> {
            if (CollUtil.isEmpty(row.getFieldMap())
                    || StrUtil.isBlank(row.getFieldMap().get(ReadFileUtils.RECEIVER_KEY))) {
                return;
            }
            HashMap<String, String> params = ReadFileUtils.getParamFromLine(row.getFieldMap());
            CrowdInfoVo crowdInfoVo = CrowdInfoVo.builder().receiver(row.getFieldMap().get(ReadFileUtils.RECEIVER_KEY))
                    .params(params).build();
            crowdBatchTaskPending.pending(crowdInfoVo);
        });
    }
}
