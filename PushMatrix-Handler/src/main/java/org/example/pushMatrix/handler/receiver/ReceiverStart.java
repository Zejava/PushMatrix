package org.example.pushMatrix.handler.receiver;

import cn.hutool.core.text.StrPool;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.example.pushMatrix.handler.utils.GroupIdMappingUtils;
import org.example.pushMatrix.support.constant.MessageQueuePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * @Author 泽
 * @Date 2024/8/7 15:42
 * 启动消费者
 */
@Service
//@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.KAFKA)
@Slf4j
public class ReceiverStart {
    @Autowired
    private ApplicationContext context;
//    @Autowired
//    private ConsumerFactory consumerFactory;

    /**
     * receiver的消费方法常量
     */
    private static final String RECEIVER_METHOD_NAME = "Receiver.consumer";

    /**
     * 获取得到所有的groupId
     */
    private static List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();

    /**
     * 下标(用于迭代groupIds位置)
     */
    private static Integer index = 0;

    /**
     * 为每个渠道不同的消息类型 创建一个Receiver对象
     */
    @PostConstruct
    public void init() {
        for (int i = 0; i < groupIds.size(); i++) {
            context.getBean(Receiver.class);
        }
    }

    /**
     * 给每个Receiver对象的consumer方法 @KafkaListener赋值相应的groupId
     */
    @Bean
    public static KafkaListenerAnnotationBeanPostProcessor.AnnotationEnhancer groupIdEnhancer() {
        return (attrs, element) -> {
            if (element instanceof Method) {
                // 就是类名.方法名
                String name = ((Method) element).getDeclaringClass().getSimpleName() + StrPool.DOT + ((Method) element).getName();
                if (RECEIVER_METHOD_NAME.equals(name)) {
                    attrs.put("groupId", groupIds.get(index++));
                }
            }
            return attrs;
        };
    }
//    /**
//     * 针对tag消息过滤
//     * producer 将tag写进header里
//     *
//     * @return true 消息将会被丢弃
//     */
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory filterContainerFactory(@Value("${austin.business.tagId.key}") String tagIdKey,
//                                                                          @Value("${austin.business.tagId.value}") String tagIdValue) {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        factory.setAckDiscarded(true);
//
//        factory.setRecordFilterStrategy(consumerRecord -> {
//            if (Optional.ofNullable(consumerRecord.value()).isPresent()) {
//                for (Header header : consumerRecord.headers()) {
//                    if (header.key().equals(tagIdKey) &&
//                            new String(header.value(), StandardCharsets.UTF_8).equals(tagIdValue)) {
//                        return false;
//                    }
//                }
//            }
//            return true;
//        });
//        return factory;
//    }
}
