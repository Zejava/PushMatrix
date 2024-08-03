import com.alibaba.fastjson.JSON;
import com.tencentcloudapi.ckafka.v20190819.models.ConsumerRecord;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.handler.SmsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @Author 泽
 * @Date 2024/8/3 17:39
 * 消费MQ中的消息
 */
@Component
@Slf4j
public class Receiver {
    @Autowired
    private SmsHandler smsHandler;

    @KafkaListener(topics = {"pushMatrix"}, groupId = "sms")
    public void consumer(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            List<TaskInfo> lists = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            for (TaskInfo taskInfo : lists) {
                smsHandler.doHandler(taskInfo);
            }
            log.info("receiver message:{}", JSON.toJSONString(lists));
        }

    }
}
