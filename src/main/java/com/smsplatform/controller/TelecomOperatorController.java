package com.smsplatform.controller;

import com.smsplatform.service.TelecomOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telco")
public class TelecomOperatorController {

    @Autowired
    private TelecomOperatorService telecomOperatorService;

    @GetMapping("${api.send.sms.url}")
    public String sendToSmsc(@RequestParam Integer account_id,
                             @RequestParam Long mobile,
                             @RequestParam String message) {
        return telecomOperatorService.processMessage(account_id, mobile, message);
    }
}
