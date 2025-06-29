package com.smsplatform.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smsplatform.model.SmsRequest;
import com.smsplatform.service.InternalDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private InternalDbService internalDbService;

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = "${sms.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeSmsRequest(String message) {
        logger.info("Received message from Kafka topic: {}", message);

        try {
            // Deserialize the message
            SmsRequest smsRequest = objectMapper.readValue(message, SmsRequest.class);
            logger.debug("Deserialized SmsRequest: {}", smsRequest);

            // Insert into the database
            internalDbService.insertSendMsg(smsRequest);
            logger.info("Successfully processed and stored SMS request with ackId: {}", smsRequest.getAckId());

        } catch (Exception e) {
            logger.error("Error processing Kafka message: {}. Error: {}", message, e.getMessage(), e);
        }
    }
}
