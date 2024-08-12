package org.example.pushMatrix.handler.discard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.example.pushMatrix.common.constant.PushMatrixConstant;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.springframework.stereotype.Service;

/**
 * @Author 泽
 * @Date 2024/8/11 15:30
 * 丢弃模板消息
 */
@Service
public class DiscardMessageService {
    private static final String DISCARD_MESSAGE_KEY = "discard";

    @ApolloConfig("pushMatrix")
    private Config config;

    /**
     * 丢弃消息，配置在apollo
     * @param taskInfo
     * @return
     */
    public boolean isDiscard(TaskInfo taskInfo) {
        JSONArray array = JSON.parseArray(config.getProperty(DISCARD_MESSAGE_KEY,
                PushMatrixConstant.APOLLO_DEFAULT_VALUE_JSON_ARRAY));
        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))) {
            return true;
        }
        return false;
    }

}
