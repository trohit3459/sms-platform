package com.smsplatform.controller;

import com.smsplatform.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerApiController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("${api.send.message.url}")
    public String sendMessage(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam Long mobile,
                              @RequestParam String message) {
        return customerService.processSmsRequest(username, password, mobile, message);
    }
}
