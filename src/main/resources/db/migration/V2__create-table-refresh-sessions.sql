CREATE TABLE refresh_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT NOT NULL,
    token_hash VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at DATETIME  NOT NULL,
    last_used_at DATETIME  NULL,
    revoked_at DATETIME  NULL,

    CONSTRAINT uk_refresh_sessions_token_hash UNIQUE (token_hash),

    CONSTRAINT fk_refresh_sessions_user FOREIGN KEY (user_id) REFERENCES tb_users (id) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_sessions_hash ON refresh_sessions (token_hash);