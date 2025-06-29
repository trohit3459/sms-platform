package com.smsplatform.service;

import com.smsplatform.kafka.KafkaProducerService;
import com.smsplatform.model.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private InternalDbService internalDbService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public String processSmsRequest(String username, String password, Long mobile, String message) {
        logger.info("Processing SMS request. Username: {}, Mobile: {}", username, mobile);

        // Validate input parameters
        if (mobile == null || String.valueOf(mobile).length() != 10 ||
                message == null || message.isEmpty() || message.length() > 160) {
            logger.warn("Validation failed. Username: {}, Mobile: {}, Message Length: {}",
                    username, mobile, message != null ? message.length() : "null");
            return "STATUS: REJECTED~~ RESPONSE_CODE: FAILURE";
        }

        try {
            // Validate username and password
            logger.debug("Validating credentials. Username: {}", username);
            Integer accountId = internalDbService.getAccountId(username, password);
            if (accountId == null) {
                logger.warn("Authentication failed for username: {}", username);
                return "STATUS: REJECTED~~ RESPONSE_CODE: FAILURE";
            }

            // Generate unique acknowledgment ID
            String ackId = UUID.randomUUID().toString();
            logger.info("Generated acknowledgment ID: {} for username: {}", ackId, username);

            // Create SMS request object
            SmsRequest smsRequest = new SmsRequest(ackId, accountId, mobile, message);
            logger.debug("Constructed SmsRequest: {}", smsRequest);

            // Send to Kafka
            kafkaProducerService.sendSmsRequest(smsRequest);
            logger.info("SMS request successfully sent to Kafka. AckId: {}, Username: {}", ackId, username);

            return "STATUS: ACCEPTED~~RESPONSE_CODE: SUCCESS~~" + ackId;

        } catch (Exception e) {
            logger.error("Error processing SMS request. Username: {}, Mobile: {}, Error: {}",
                    username, mobile, e.getMessage(), e);
            return "STATUS: REJECTED~~ RESPONSE_CODE: FAILURE";
        }
    }
}
