package com.smsplatform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TelecomOperatorService {

    private static final Logger logger = LoggerFactory.getLogger(TelecomOperatorService.class);

    public String processMessage(Integer accountId, Long mobile, String message) {
        logger.info("Processing message for Account ID: {}, Mobile: {}", accountId, mobile);

        // Validate input parameters
        if (accountId == null || mobile == null || message == null || message.isEmpty() ||
                mobile.toString().length() != 10 || message.length() > 160) {
            logger.warn("Validation failed. Account ID: {}, Mobile: {}, Message Length: {}",
                    accountId, mobile, message != null ? message.length() : "null");
            return "STATUS: REJECTED~~ RESPONSE_CODE: FAILURE";
        }

        try {
            // Simulate processing
            String ackId = UUID.randomUUID().toString();
            logger.info("Message processed successfully. AckId: {}, Account ID: {}, Mobile: {}", ackId, accountId, mobile);
            return "STATUS: ACCEPTED~~RESPONSE_CODE: SUCCESS~~" + ackId;
        } catch (Exception e) {
            logger.error("Error processing message for Account ID: {}, Mobile: {}. Error: {}",
                    accountId, mobile, e.getMessage(), e);
            return "STATUS: REJECTED~~ RESPONSE_CODE: FAILURE";
        }
    }
}
