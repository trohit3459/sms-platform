CREATE TABLE IF NOT EXISTS users (
    account_id INTEGER PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Create send_msg table
CREATE TABLE IF NOT EXISTS send_msg (
    id SERIAL PRIMARY KEY,
    mobile BIGINT NOT NULL,
    message VARCHAR(160) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'NEW',
    received_ts TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sent_ts TIMESTAMP,
    account_id INTEGER NOT NULL,
    telco_response TEXT,
    FOREIGN KEY (account_id) REFERENCES users(account_id)
);

-- Insert sample users
INSERT INTO users (account_id, username, password) VALUES (1001, 'user1', 'password1') ON CONFLICT DO NOTHING;
INSERT INTO users (account_id, username, password) VALUES (1002, 'user2', 'password2') ON CONFLICT DO NOTHING;


