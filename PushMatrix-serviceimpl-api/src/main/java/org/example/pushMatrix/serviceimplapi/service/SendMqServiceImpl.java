package org.example.pushMatrix.serviceimplapi.service;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/15 10:57
 */
@Slf4j
@Service

public class SendMqServiceImpl implements SendMqService{
    @Autowired
    private KafkaTemplate kafkaTemplate;

//    @Value("${austin.business.tagId.key}")
//    private String tagIdKey;
@Value("${PushMatrix.topic.name}")
private String topicName;
    @Override
    public void send(String topic, String jsonValue, String tagId) {
//        if (CharSequenceUtil.isNotBlank(tagId)) {
//            List<Header> headers = Arrays.asList(new RecordHeader(tagIdKey, tagId.getBytes(StandardCharsets.UTF_8)));
//            kafkaTemplate.send(new ProducerRecord(topic, null, null, null, jsonValue, headers));
//            return;
//        }
        kafkaTemplate.send(topic, jsonValue);
    }

    @Override
    public void send(String topicName, String jsonValue) {
        send(this.topicName, jsonValue, null);
    }
}
