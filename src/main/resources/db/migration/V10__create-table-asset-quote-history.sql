CREATE TABLE tb_asset_quote_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    asset_id BIGINT NOT NULL,
    price DECIMAL(19,4) NOT NULL,
    quote_date DATE NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_asset_quote_history_assets FOREIGN KEY (asset_id) REFERENCES tb_assets (id),
    CONSTRAINT uk_asset_quote_history_asset_date UNIQUE (asset_id, quote_date)
);