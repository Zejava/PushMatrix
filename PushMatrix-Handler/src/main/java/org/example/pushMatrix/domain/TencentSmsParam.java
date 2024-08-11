package org.example.pushMatrix.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 泽
 * @Date 2024/8/11 8:02
 * 腾讯云短信发送的相关参数 主要存腾讯云账号发送短信的一些相关配置信息
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TencentSmsParam {
    /**
     * api相关
     */
    private String url;
    private String region ;

    /**
     * 账号相关
     */
    private String secretId;
    private String secretKey;
    private String smsSdkAppId;
    private String templateId;
    private String signName;

    /**
     * 标识渠道商Id
     */
    private Integer supplierId;

    /**
     * 标识渠道商名字
     */
    private String supplierName;
}
