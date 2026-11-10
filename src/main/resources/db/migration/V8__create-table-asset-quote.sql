CREATE TABLE tb_account_assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    account_id BIGINT NOT NULL,
    asset_id BIGINT NOT NULL,
    quantity BIGINT NOT NULL,
    average_price DECIMAL(15,2) NOT NULL,
    total_invested DECIMAL(15,2) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_account_assets_account FOREIGN KEY (account_id) REFERENCES tb_accounts (id),
    CONSTRAINT account_assets FOREIGN KEY (asset_id) REFERENCES tb_assets (id),

    CONSTRAINT uq_account_asset UNIQUE (account_id, asset_id)
);