package org.example.pushMatrix;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.example.pushMatrix.domain.SmsParam;
import org.example.pushMatrix.script.TencentSmsScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
@SpringBootApplication
@RestController
@EnableJpaRepositories(basePackages = "org.example.pushMatrix.dao")
public class PushMatrixApplication {
@Autowired
private TencentSmsScript tencentSmsScript;

    public static void main(String[] args) {
        SpringApplication.run(PushMatrixApplication.class, args);
    }
    /**
     * @param phone 手机号
     * @return
     */
    @GetMapping("/sendSms")
    public String sendSms(String phone,String content) throws TencentCloudSDKException {

        /**
         * 这里的content指的是模板占位符的参数值
         */
        SmsParam smsParam = SmsParam.builder()
                .phones(new HashSet<>(Arrays.asList(phone)))
                .content(content)
                .build();
        System.out.println();
        return tencentSmsScript.send(smsParam).toString();
    }
}
