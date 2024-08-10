package org.example.pushMatrix.deduplication.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.enums.DeduplicationType;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/8 17:32
 * 对内容进行去重服务
 */
@Service
public class ContentDeduplicationService extends AbstractDeduplicationService {
    public ContentDeduplicationService() {
        deduplicationType = DeduplicationType.CONTENT.getCode();
    }

    /**
     * 内容去重 构建key
     * <p>
     * key: md5(templateId + receiver + content)
     * <p>
     * 相同的内容相同的模板短时间内发给同一个人
     *
     * @param taskInfo
     * @return
     */
    @Override
    public String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        return DigestUtil.md5Hex(taskInfo.getMessageTemplateId() + receiver
                + JSON.toJSONString(taskInfo.getContentModel()));
    }
}
