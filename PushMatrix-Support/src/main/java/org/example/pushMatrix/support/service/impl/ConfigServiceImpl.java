package org.example.pushMatrix.support.service.impl;

import cn.hutool.core.text.StrPool;
import cn.hutool.setting.dialect.Props;
import com.ctrip.framework.apollo.Config;
import org.example.pushMatrix.support.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @Author 泽
 * @Date 2024/8/14 22:02
 * 读取配置实现类
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    /**
     * 获取的本地配置
     */
    private static final String PROPERTIES_PATH = "local.properties";
    private Props props = new Props(PROPERTIES_PATH, StandardCharsets.UTF_8);

    /**
     * apollo配置
     */
    @Value("${apollo.bootstrap.enabled}")
    private Boolean enableApollo;
    @Value("${apollo.bootstrap.namespaces}")
    private String namespaces;



    @Override
    public String getProperty(String key, String defaultValue) {
        if (Boolean.TRUE.equals(enableApollo)) {
            Config config = com.ctrip.framework.apollo.ConfigService.getConfig(namespaces);
            return config.getProperty(key, defaultValue);
        } else {
            return props.getProperty(key, defaultValue);
        }
    }
}
