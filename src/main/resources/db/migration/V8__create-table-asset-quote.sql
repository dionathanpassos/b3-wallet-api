CREATE TABLE tb_asset_quotes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    asset_id BIGINT NOT NULL UNIQUE,
    price DECIMAL(19,4) NOT NULL,
    `change` DECIMAL(19,4) NOT NULL,
    change_percent DECIMAL(9,4) NOT NULL,
    day_high DECIMAL(19,4) NOT NULL,
    day_low DECIMAL(19,4) NOT NULL,
    previous_close DECIMAL(19,4) NOT NULL,
    volume BIGINT NOT NULL,
    quote_time TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_asset_quote_assets FOREIGN KEY (asset_id) REFERENCES tb_assets (id)
);