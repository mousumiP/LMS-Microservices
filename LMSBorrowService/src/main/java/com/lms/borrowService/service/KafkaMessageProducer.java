package com.lms.borrowService.service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaMessageProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, Object message) {
        kafkaTemplate.send(topicName, message);
        System.out.println("Published message: " + message + " to topic: " + topicName);
    }
}