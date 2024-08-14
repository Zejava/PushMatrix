package org.example.pushMatrix.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.example.pushMatrix.common.constant.PushMatrixConstant;
import org.example.pushMatrix.common.enums.AuditStatus;
import org.example.pushMatrix.common.enums.MessageStatus;
import org.example.pushMatrix.common.enums.RespStatusEnum;
import org.example.pushMatrix.common.enums.TemplateType;
import org.example.pushMatrix.common.vo.BasicResultVO;
import org.example.pushMatrix.cron.xxl.entity.XxlJobInfo;
import org.example.pushMatrix.cron.xxl.service.CronTaskService;
import org.example.pushMatrix.cron.xxl.utils.XxlJobUtils;
import org.example.pushMatrix.service.MessageTemplateService;
import org.example.pushMatrix.support.dao.MessageTemplateDao;
import org.example.pushMatrix.support.domain.MessageTemplate;
import org.example.pushMatrix.vo.MessageTemplateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/13 0:05
 */
@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {
    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Autowired
    private CronTaskService cronTaskService;

    @Autowired
    private XxlJobUtils xxlJobUtils;

    @Override
    public List<MessageTemplate> queryList(MessageTemplateParam param) {
        PageRequest pageRequest = PageRequest.of(param.getPage() - 1, param.getPerPage());
        return messageTemplateDao.findAllByIsDeletedEquals(PushMatrixConstant.FALSE, pageRequest);
    }

    @Override
    public Long count() {
        return messageTemplateDao.countByIsDeletedEquals(PushMatrixConstant.FALSE);
    }

    @Override
    public MessageTemplate saveOrUpdate(MessageTemplate messageTemplate) {
        if (messageTemplate.getId() == null) {
            initStatus(messageTemplate);
        } else {
            resetStatus(messageTemplate);
        }
        //时间戳
        messageTemplate.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        return messageTemplateDao.save(messageTemplate);
    }


    @Override
    public void deleteByIds(List<Long> ids) {
        Iterable<MessageTemplate> messageTemplates = messageTemplateDao.findAllById(ids);
        messageTemplates.forEach(messageTemplate -> messageTemplate.setIsDeleted(PushMatrixConstant.TRUE));
        for (MessageTemplate messageTemplate : messageTemplates) {
            if (messageTemplate.getCronTaskId() > 0) {
                cronTaskService.deleteCronTask(messageTemplate.getCronTaskId());
            }
        }
        messageTemplateDao.saveAll(messageTemplates);
    }

    @Override
    public MessageTemplate queryById(Long id) {
        return messageTemplateDao.findById(id).get();
    }

    @Override
    public void copy(Long id) {
        MessageTemplate messageTemplate = messageTemplateDao.findById(id).get();
        MessageTemplate clone = ObjectUtil.clone(messageTemplate).setId(null).setCronTaskId(null);
        messageTemplateDao.save(clone);
    }

    @Override
    public BasicResultVO startCronTask(Long id) {
        // 1.修改模板状态
        MessageTemplate messageTemplate = messageTemplateDao.findById(id).get();

        // 2.动态创建或更新定时任务
        XxlJobInfo xxlJobInfo = xxlJobUtils.buildXxlJobInfo(messageTemplate);

        // 3.获取taskId(如果本身存在则复用原有任务，如果不存在则得到新建后任务ID)
        Integer taskId = messageTemplate.getCronTaskId();
        BasicResultVO basicResultVO = cronTaskService.saveCronTask(xxlJobInfo);
        if (taskId == null && RespStatusEnum.SUCCESS.getCode().equals(basicResultVO.getStatus()) && basicResultVO.getData() != null) {
            taskId = Integer.valueOf(String.valueOf(basicResultVO.getData()));
        }

        // 4. 启动定时任务
        if (taskId != null) {
            cronTaskService.startCronTask(taskId);
            MessageTemplate clone = ObjectUtil.clone(messageTemplate).setMsgStatus(MessageStatus.RUN.getCode()).setCronTaskId(taskId).setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
            messageTemplateDao.save(clone);
            return BasicResultVO.success();
        }
        return BasicResultVO.fail();
    }

    @Override
    public BasicResultVO stopCronTask(Long id) {
        // 1.修改模板状态
        MessageTemplate messageTemplate = messageTemplateDao.findById(id).get();
        MessageTemplate clone = ObjectUtil.clone(messageTemplate).setMsgStatus(MessageStatus.STOP.getCode()).setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        messageTemplateDao.save(clone);

        // 2.暂停定时任务
        return cronTaskService.stopCronTask(clone.getCronTaskId());
    }


    /**
     * 初始化消息模板的状态信息
     * TODO 创建者 修改者 团队
     *
     * @param messageTemplate
     */
    private void initStatus(MessageTemplate messageTemplate) {
        messageTemplate.setFlowId(StrUtil.EMPTY)
                .setMsgStatus(MessageStatus.INIT.getCode()).setAuditStatus(AuditStatus.WAIT_AUDIT.getCode())
                .setCreator("泽").setUpdator("泽").setTeam("泽").setAuditor("泽")
                .setCreated(Math.toIntExact(DateUtil.currentSeconds()))
                .setIsDeleted(PushMatrixConstant.FALSE);

    }

    /**
     * 1. 重置模板的状态
     * 2. 修改定时任务信息(如果存在)
     *
     * @param messageTemplate
     */
    private void resetStatus(MessageTemplate messageTemplate) {
        //1.更新消息模板状态
        messageTemplate.setUpdator(messageTemplate.getUpdator())
                .setMsgStatus(MessageStatus.INIT.getCode()).setAuditStatus(AuditStatus.WAIT_AUDIT.getCode());
        //判断消息模板是否有定时任务id且定时类消息模板
        if (messageTemplate.getCronTaskId() != null && TemplateType.CLOCKING.getCode().equals(messageTemplate.getTemplateType())) {
            //2.停止原定时任务
            cronTaskService.stopCronTask(messageTemplate.getCronTaskId());
            //2.修改定时任务信息并启动
            XxlJobInfo xxlJobInfo = xxlJobUtils.buildXxlJobInfo(messageTemplate);
            cronTaskService.saveCronTask(xxlJobInfo);
            cronTaskService.startCronTask(messageTemplate.getCronTaskId());

        }
    }
}
