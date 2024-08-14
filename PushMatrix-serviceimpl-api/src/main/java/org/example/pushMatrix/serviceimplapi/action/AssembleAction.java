package org.example.pushMatrix.serviceimplapi.action;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.common.constant.PushMatrixConstant;
import org.example.pushMatrix.support.dao.MessageTemplateDao;
import org.example.pushMatrix.serviceapi.domain.MessageParam;
import org.example.pushMatrix.support.domain.MessageTemplate;
import org.example.pushMatrix.serviceimplapi.domain.SendTaskModel;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.dto.ContentModel;
import org.example.pushMatrix.common.enums.ChannelType;
import org.example.pushMatrix.common.enums.RespStatusEnum;
import org.example.pushMatrix.common.pipeline.BusinessProcess;
import org.example.pushMatrix.common.pipeline.ProcessContext;
import org.example.pushMatrix.support.utils.ContentHolderUtil;
import org.example.pushMatrix.support.utils.TaskInfoUtils;
import org.example.pushMatrix.common.vo.BasicResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author 泽
 * @Date 2024/8/3 22:57
 * 用来拼装SendTaskModel的参数
 */
@Slf4j
@Component
public class AssembleAction implements BusinessProcess {
    @Autowired
    private MessageTemplateDao messageTemplateDao;//jpa 存入数据库持久化

    @Override
    public void process(ProcessContext context) {
        SendTaskModel sendTaskModel = (SendTaskModel) context.getProcessModel();//获得当下责任链中的数据信息
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();
        try {
            Optional<MessageTemplate> messageTemplate = messageTemplateDao.findById(messageTemplateId);//数据库查找模板
            if (!messageTemplate.isPresent() || messageTemplate.get().getIsDeleted().equals(PushMatrixConstant.TRUE)) {//模板为空或模板标记为已删除
                context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TEMPLATE_NOT_FOUND));//中断责任链并返回结果
                return;
            }
            List<TaskInfo> taskInfos = assembleTaskInfo(sendTaskModel, messageTemplate.get());
            sendTaskModel.setTaskInfo(taskInfos);
        }
        catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("assemble task fail! templateId:{}, e:{}", messageTemplateId, Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 组装 TaskInfo 任务消息
     *
     * @param sendTaskModel 发送消息的任务模型参数
     * @param messageTemplate  消息模板
     */
    private List<TaskInfo> assembleTaskInfo(SendTaskModel sendTaskModel, MessageTemplate messageTemplate) {
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        List<TaskInfo> taskInfoList = new ArrayList<>();

        for (MessageParam messageParam : messageParamList) {
            TaskInfo taskInfo = TaskInfo.builder()
                    .messageTemplateId(messageTemplate.getId())
                    //利用id生成工具生成businessId,实现唯一id
                    .businessId(TaskInfoUtils.generateBusinessId(messageTemplate.getId(), messageTemplate.getTemplateType()))
                    //分割字符串后转为链表传递给set，保证receiver的唯一性
                    .receiver(new HashSet<>(Arrays.asList(messageParam.getReceiver().split(String.valueOf(StrUtil.C_COMMA)))))
                    .idType(messageTemplate.getIdType())
                    .sendChannel(messageTemplate.getSendChannel())
                    .templateType(messageTemplate.getTemplateType())
                    .msgType(messageTemplate.getMsgType())
                    .shieldType(messageTemplate.getShieldType())
                    .sendAccount(messageTemplate.getSendAccount())
                    .contentModel(getContentModelValue(messageTemplate, messageParam))
                    .build();
            taskInfoList.add(taskInfo);
        }

        return taskInfoList;
    }

    /**
     * 利用反射去获取 contentModel,替换占位符信息
     */
    private static ContentModel getContentModelValue(MessageTemplate messageTemplate, MessageParam messageParam) {
        //通过反射获得对应消息模型类
        Integer sendChannel = messageTemplate.getSendChannel();
        Class contentModelClass = ChannelType.getChanelModelClassByCode(sendChannel);

        //// 得到模板的 msgContent 和 入参
        Map<String, String> variables = messageParam.getVariables();
        JSONObject jsonObject = JSON.parseObject(messageTemplate.getMsgContent());
        /**
         *  反射获取得到不同的渠道对应的contentModel
         *  此处使用了hutool包的ReflectUtil工具类去做了反射异常校验，
         *        通过标准java库需要处理一些繁琐的 try-catch 块来确保代码的健壮性和异常处理，比如
         *        字段类型转换等
         */
        Field[] fields = ReflectUtil.getFields(contentModelClass);
        ContentModel contentModel = (ContentModel) ReflectUtil.newInstance(contentModelClass);
        for (Field field : fields) {
            String originValue = jsonObject.getString(field.getName());
            //            进行替换，把反射对象设置对应的内容
            if (StrUtil.isNotBlank(originValue)) {
                String resultValue = ContentHolderUtil.replacePlaceHolder(originValue, variables);
                //获得后如果是bean对象就进行转换成bean，如果不是保持原来的字符串
                ReflectUtil.setFieldValue(contentModel, field, resultValue);
            }
        }
        return contentModel;
    }
}