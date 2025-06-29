package com.smsplatform.controller;

import com.smsplatform.service.InternalDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class InternalDbApiController {

    @Autowired
    private InternalDbService internalDbService;

    @GetMapping("${api.account.id.url}")
    public Integer getAccountId(@RequestParam String username, @RequestParam String password) {
        return internalDbService.getAccountId(username, password);
    }
}
