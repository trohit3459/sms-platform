package com.smsplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsRequest {
    @JsonProperty("ack_id")
    private String ackId;

    @JsonProperty("account_id")
    private Integer accountId;

    private Long mobile;
    private String message;

    public SmsRequest() {
    }

    public SmsRequest(String ackId, Integer accountId, Long mobile, String message) {
        this.ackId = ackId;
        this.accountId = accountId;
        this.mobile = mobile;
        this.message = message;
    }

    // Getters and Setters
    public String getAckId() {
        return ackId;
    }

    public void setAckId(String ackId) {
        this.ackId = ackId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
