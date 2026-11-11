ALTER TABLE tb_transactions
    DROP FOREIGN KEY fk_transactions_broker,
    DROP COLUMN broker_id;