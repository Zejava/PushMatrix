package org.example.pushMatrix.kafkatest;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author 泽
 * @Date 2024/8/3 10:53
 */
@Component
@Slf4j
public class UserProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 发送数据
     *
     * @param userid
     */
    public void sendLog(String userid) {
        User user = new User();
        user.setUsername("jhp").setUserid(userid).setState("0");
        System.err.println("发送用户日志数据:" + user);
        kafkaTemplate.send("pushMatrix", JSON.toJSONString(user));
    }

}
