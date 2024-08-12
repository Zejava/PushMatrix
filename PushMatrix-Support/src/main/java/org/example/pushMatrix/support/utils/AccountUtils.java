package org.example.pushMatrix.support.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.example.pushMatrix.common.constant.PushMatrixConstant;
import org.springframework.stereotype.Component;

/**
 * @Author 泽
 * @Date 2024/8/11 7:43
 * 账号工具类
 */
@Component
public class AccountUtils {
@ApolloConfig("pushMatrix")
    private Config config;

    /**
     *用来获取账号的相关配置
     * @param sendAccount 发送信息的账号
     * @param apolloKey 业务的渠道
     * @param prefix 业务的前缀
     * @param t 业务渠道的类型
     * @return
     * @param <T>
     */
    public <T> T getAccount(Integer sendAccount, String apolloKey, String prefix, T t)
    {
        String accountValues = config.getProperty(apolloKey, PushMatrixConstant.APOLLO_DEFAULT_VALUE_JSON_ARRAY);
        JSONArray jsonArray = JSON.parseArray(accountValues);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object object = jsonObject.getObject(prefix + sendAccount, t.getClass());
            if (object != null) {
                return (T) object;
            }
        }
        return null;
    }
}
