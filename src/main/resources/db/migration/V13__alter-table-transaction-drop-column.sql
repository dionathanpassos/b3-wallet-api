CREATE TABLE tb_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    account_id BIGINT NOT NULL,
    asset_id BIGINT NOT NULL,
    broker_id BIGINT NOT NULL,
    transaction_type VARCHAR(12) NOT NULL,
    quantity BIGINT NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    transaction_date DATE NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_transactions_account FOREIGN KEY (account_id) REFERENCES tb_accounts (id),
    CONSTRAINT fk_transactions_asset FOREIGN KEY (asset_id) REFERENCES tb_assets (id),
    CONSTRAINT fk_transactions_broker FOREIGN KEY (broker_id) REFERENCES tb_brokers (id)
);
