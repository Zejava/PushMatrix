package org.example.pushMatrix.support.utils;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/8 17:16
 * 对redis的操作进行二次封装
 */
@Component
@Slf4j
public class RedisUtils {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *将结果转为map
     * @param keys
     * @return
     */
    public Map<String, String> mGet(List<String> keys) {
        HashMap<String, String> result = new HashMap<>(keys.size());//keys长度就是map长度
        try {
            //批量取出value值
            List<String> value = redisTemplate.opsForValue().multiGet(keys);
            if (CollUtil.isNotEmpty(value)) {//hutool包进行value值校验
                for (int i = 0; i < keys.size(); i++) {
                    result.put(keys.get(i), value.get(i));//结果存入result中，
                }
            }
        } catch (Exception e) {
            log.error("redis mGet fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 使用 Redis 管道（pipeline）功能来批量设置键值对及其过期时间。
     * 通过 RedisTemplate 的 executePipelined 方法，将多个命令打包发送到 Redis 服务器，减少I/O次数，提高性能
     * @param keyValues
     * @param seconds
     */
    public void pipelineSetEX(Map<String, String> keyValues, Long seconds) {
        try {
            redisTemplate.executePipelined((RedisCallback<String>) connection -> {//通过entrySet遍历map
                for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                    connection.setEx(entry.getKey().getBytes(), seconds,
                            entry.getValue().getBytes());//setEx 方法需要键和值的字节表示，因此使用了 getBytes() 方法进行转换
                }
                return null;
            });
        } catch (Exception e) {
            log.error("redis pipelineSetEX fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }
}
