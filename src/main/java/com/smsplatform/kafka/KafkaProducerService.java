package com.smsplatform.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smsplatform.model.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${sms.kafka.topic}")
    private String topicName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendSmsRequest(SmsRequest smsRequest) {
        try {
            // Convert SmsRequest to JSON
            String message = objectMapper.writeValueAsString(smsRequest);
            logger.info("Sending SMS request to Kafka. Topic: {}, Message: {}", topicName, message);

            // Send message to Kafka
            kafkaTemplate.send(topicName, message);
            logger.info("Successfully sent SMS request to Kafka. AckId: {}", smsRequest.getAckId());

        } catch (Exception e) {
            logger.error("Failed to send SMS request to Kafka. AckId: {}. Error: {}", smsRequest.getAckId(), e.getMessage(), e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
