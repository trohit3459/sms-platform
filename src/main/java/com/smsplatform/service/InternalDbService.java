package com.smsplatform.service;

import com.smsplatform.model.SendMsg;
import com.smsplatform.model.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class InternalDbService {

    private static final Logger logger = LoggerFactory.getLogger(InternalDbService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer getAccountId(String username, String password) {
        try {
            logger.info("Fetching account ID for username: {}", username);
            String sql = "SELECT account_id FROM users WHERE username = ? AND password = ?";
            Integer accountId = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
            logger.debug("Retrieved account ID: {} for username: {}", accountId, username);
            return accountId;
        } catch (Exception e) {
            logger.error("Error fetching account ID for username: {}. Error: {}", username, e.getMessage(), e);
            return null;
        }
    }

    public void insertSendMsg(SmsRequest smsRequest) {
        try {
            logger.info("Inserting new message for mobile: {}", smsRequest.getMobile());
            String sql = "INSERT INTO send_msg(mobile, message, status, received_ts, account_id) VALUES (?, ?, 'NEW', CURRENT_TIMESTAMP, ?)";
            jdbcTemplate.update(sql, smsRequest.getMobile(), smsRequest.getMessage(), smsRequest.getAccountId());
            logger.debug("Message inserted successfully for ackId: {}", smsRequest.getAckId());
        } catch (Exception e) {
            logger.error("Error inserting message for mobile: {}. Error: {}", smsRequest.getMobile(), e.getMessage(), e);
        }
    }

    public List<SendMsg> getNewMessages() {
        try {
            logger.info("Fetching new messages from database.");
            String sql = "SELECT * FROM send_msg WHERE status = 'NEW'";
            List<SendMsg> newMessages = jdbcTemplate.query(sql, new SendMsgRowMapper());
            logger.info("Retrieved {} new messages from database.", newMessages.size());
            return newMessages;
        } catch (Exception e) {
            logger.error("Error fetching new messages from database. Error: {}", e.getMessage(), e);
            return List.of();
        }
    }

    public void updateMessageStatus(Long id, String status) {
        try {
            logger.info("Updating status to '{}' for message ID: {}", status, id);
            String sql = "UPDATE send_msg SET status = ? WHERE id = ?";
            jdbcTemplate.update(sql, status, id);
            logger.debug("Status updated to '{}' for message ID: {}", status, id);
        } catch (Exception e) {
            logger.error("Error updating status for message ID: {}. Error: {}", id, e.getMessage(), e);
        }
    }

    public void updateMessageResponse(Long id, String response) {
        try {
            logger.info("Updating response for message ID: {}", id);
            String sql = "UPDATE send_msg SET status = 'SENT', telco_response = ?, sent_ts = CURRENT_TIMESTAMP WHERE id = ?";
            jdbcTemplate.update(sql, response, id);
            logger.debug("Response updated for message ID: {}", id);
        } catch (Exception e) {
            logger.error("Error updating response for message ID: {}. Error: {}", id, e.getMessage(), e);
        }
    }

    private static class SendMsgRowMapper implements RowMapper<SendMsg> {
        @Override
        public SendMsg mapRow(ResultSet rs, int rowNum) throws SQLException {
            SendMsg sendMsg = new SendMsg();
            sendMsg.setId(rs.getLong("id"));
            sendMsg.setMobile(rs.getLong("mobile"));
            sendMsg.setMessage(rs.getString("message"));
            sendMsg.setStatus(rs.getString("status"));
            sendMsg.setAccountId(rs.getInt("account_id"));
            sendMsg.setTelcoResponse(rs.getString("telco_response"));
            return sendMsg;
        }
    }
}
