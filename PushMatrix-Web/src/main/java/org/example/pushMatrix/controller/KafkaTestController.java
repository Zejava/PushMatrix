package org.example.pushMatrix.controller;

import org.example.pushMatrix.kafkatest.User;
import org.example.pushMatrix.kafkatest.UserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 泽
 * @Date 2024/8/3 10:57
 */
@RestController
public class KafkaTestController {
    @Autowired
    private UserProducer userLogProducer;

    /**
     * test insert
     */
    @GetMapping("/kafka/insert")
    public String insert(String userId) {
        userLogProducer.sendLog(userId);
        return null;
    }
}
