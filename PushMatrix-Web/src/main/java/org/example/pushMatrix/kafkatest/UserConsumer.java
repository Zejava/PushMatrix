package org.example.pushMatrix.kafkatest;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author 泽
 * @Date 2024/8/3 10:54
 */
@Component
@Slf4j
public class UserConsumer {
    @KafkaListener(topics = {"pushMatrix"},groupId = "austinGroup1")
    public void consumer(ConsumerRecord<?,?> consumerRecord){
        //判断是否为null
        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        log.info(">>>>>>>>>> record =" + kafkaMessage);
        if(kafkaMessage.isPresent()){
            //得到Optional实例中的值
            Object message = kafkaMessage.get();
            System.err.println("消费消息:"+message);
        }

    }
}
