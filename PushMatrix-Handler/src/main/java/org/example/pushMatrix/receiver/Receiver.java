package org.example.pushMatrix.receiver;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.pending.Task;
import org.example.pushMatrix.pending.TaskPendingHolder;
import org.example.pushMatrix.utils.GroupIdMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @Author 泽
 * @Date 2024/8/7 15:42
 * 消费MQ的消息
 */
@Slf4j
public class Receiver {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TaskPendingHolder taskPendingHolder;

    @KafkaListener(topics = "#{'${PushMatrix.topic.name}'}")
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String topicGroupId) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            List<TaskInfo> TaskInfoLists = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            String messageGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(TaskInfoLists.get(0));

            /**
             * 每个消费者组 只消费 他们自身关心的消息
             */
            if (topicGroupId.equals(messageGroupId)) {
                for (TaskInfo taskInfo : TaskInfoLists) {
                    Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
                    taskPendingHolder.route(topicGroupId).execute(task);
                }
            }
        }

    }
}
