package com.smsplatform.service;

import com.smsplatform.model.SendMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BackEndThreadService {

    private static final Logger logger = LoggerFactory.getLogger(BackEndThreadService.class);

    @Autowired
    private InternalDbService internalDbService;

    @Value("${telecom.operator.url}")
    private String telecomOperatorUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedDelayString = "${sms.thread.interval}")
    public void processNewMessages() {
        logger.info("Starting background thread to process new messages.");

        try {
            List<SendMsg> newMessages = internalDbService.getNewMessages();
            logger.info("Retrieved {} new messages for processing.", newMessages.size());

            for (SendMsg sendMsg : newMessages) {
                logger.debug("Processing message with ID: {}", sendMsg.getId());

                // Update status to INPROCESS
                internalDbService.updateMessageStatus(sendMsg.getId(), "INPROCESS");
                logger.info("Message ID {} status updated to INPROCESS.", sendMsg.getId());

                // Call Telecom Operator API
                String url = String.format("%s?account_id=%d&mobile=%d&message=%s",
                        telecomOperatorUrl, sendMsg.getAccountId(), sendMsg.getMobile(), sendMsg.getMessage());
                logger.debug("Calling Telecom Operator API with URL: {}", url);

                try {
                    String response = restTemplate.getForObject(url, String.class);
                    logger.info("Received response for message ID {}: {}", sendMsg.getId(), response);
                    internalDbService.updateMessageResponse(sendMsg.getId(), response);
                } catch (Exception e) {
                    logger.error("Error calling Telecom Operator API for message ID {}. Error: {}", sendMsg.getId(), e.getMessage(), e);
                    internalDbService.updateMessageResponse(sendMsg.getId(), "STATUS: REJECTED~~ RESPONSE_CODE: FAILURE");
                }
            }
        } catch (Exception e) {
            logger.error("Error in background thread: {}", e.getMessage(), e);
        }

        logger.info("Background thread completed processing messages.");
    }
}
