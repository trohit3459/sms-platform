package com.smsplatform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmsPlatformDemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(SmsPlatformDemoApplication.class);

    public static void main(String[] args) {
        logger.info("Starting SMS Platform Demo Application...");
        SpringApplication.run(SmsPlatformDemoApplication.class, args);
        logger.info("SMS Platform Demo Application started successfully.");
    }
}