package org.example.pushMatrix.service.deduplication;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import org.example.pushMatrix.domain.TaskInfo;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/8 17:32
 * 对内容进行去重服务
 */
@Service
public class ContentAbstractDeduplicationService extends AbstractDeduplicationService{
    /**
     * 内容去重 构建key
     * <p>
     * key: md5(templateId + receiver + content)
     * 相同的内容相同的模板短时间内发给同一个人
     * @param taskInfo
     * @return
     */
    @Override
    public String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        //生成一个任务信息的唯一标识符（通过MD5哈希），将任务模板，接收者，内容进行拼接再进行md5码加密
        return DigestUtil.md5Hex(taskInfo.getMessageTemplateId() + receiver
                + JSON.toJSONString(taskInfo.getContentModel()));
    }
}
