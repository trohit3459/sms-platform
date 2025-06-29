package com.smsplatform.model;

import java.time.LocalDateTime;

public class SendMsg {
    private Long id;
    private Long mobile;
    private String message;
    private String status;
    private LocalDateTime receivedTs;
    private LocalDateTime sentTs;
    private Integer accountId;
    private String telcoResponse;

    public SendMsg() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getReceivedTs() {
        return receivedTs;
    }

    public void setReceivedTs(LocalDateTime receivedTs) {
        this.receivedTs = receivedTs;
    }

    public LocalDateTime getSentTs() {
        return sentTs;
    }

    public void setSentTs(LocalDateTime sentTs) {
        this.sentTs = sentTs;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getTelcoResponse() {
        return telcoResponse;
    }

    public void setTelcoResponse(String telcoResponse) {
        this.telcoResponse = telcoResponse;
    }
}
