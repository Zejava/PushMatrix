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
    public void consumer(ConsumerRecord consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.getValue());//用Optional.ofNullable（）方法包装值
        if (kafkaMessage.isPresent()) {//避免直接调用可能为null的对象的方法而导致的NullPointerException
            List<TaskInfo> lists = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            for (TaskInfo taskInfo : lists) {//可以考虑并行流或者批量处理提高性能 todo
                smsHandler.doHandler(taskInfo);
            }
            log.info("receiver message:{}", JSON.toJSONString(lists));
        }

    }
}
