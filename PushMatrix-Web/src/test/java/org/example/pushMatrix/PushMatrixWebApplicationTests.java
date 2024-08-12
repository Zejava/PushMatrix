package org.example.pushMatrix;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest
class PushMatrixWebApplicationTests {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    static final String a = "pushMatrix";

@Autowired
private RedisTemplate redisTemplate;
//测试redis是否正常连接
    @Test
    public void contextLoads() {
        redisTemplate.opsForValue().set("泽","19");
        System.out.println(redisTemplate.opsForValue().get("泽"));
    }
    //连接数据库测试
    @Test
    public void test() {
        String message = "吃饭";
        kafkaTemplate.send(a, message);
        System.out.println("Message sent successfully!");

    }
@Test
@KafkaListener(topics = a, groupId = "email.auth_code")
public void Consume() {
    String message = "吃饭";
        System.out.println("Received message: " + message);

}

}
