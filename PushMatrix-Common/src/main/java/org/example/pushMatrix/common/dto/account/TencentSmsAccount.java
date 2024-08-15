package org.example.pushMatrix.common.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Author 泽
 * @Date 2024/8/15 17:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TencentSmsAccount extends SmsAccount{
    /**
     * api相关
     */
    private String url;
    private String region;

    /**
     * 账号相关
     */
    private String secretId;
    private String secretKey;
    private String smsSdkAppId;
    private String templateId;
    private String signName;

    /**
     * 重写equals方法
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TencentSmsAccount that = (TencentSmsAccount) o;
        return url.equals(that.url) &&
                region.equals(that.region) &&
                secretId.equals(that.secretId) &&
                secretKey.equals(that.secretKey) &&
                smsSdkAppId.equals(that.smsSdkAppId) &&
                templateId.equals(that.templateId) &&
                signName.equals(that.signName);
    }

    /**
     * 重写hashCode方法
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), url, region, secretId, secretKey, smsSdkAppId, templateId, signName);
    }
}
