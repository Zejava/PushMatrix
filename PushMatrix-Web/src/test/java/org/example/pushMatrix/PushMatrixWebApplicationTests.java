package org.example.pushMatrix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest
class PushMatrixWebApplicationTests {
    @Autowired
    private JpaService jpaService;
@Autowired
private RedisTemplate redisTemplate;
//测试redis是否正常连接
    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("泽","19");
        System.out.println(redisTemplate.opsForValue().get("泽"));
    }
    //连接数据库测试
    @Test
    public void test(){
        SmsParam smsParam = SmsParam.builder().
                phones(new HashSet<>(Arrays.asList("231")))
                .content("吃饭")
                .build();

        jpaService.insert(smsParam);
    }


}
