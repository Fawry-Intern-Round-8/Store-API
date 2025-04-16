CREATE TABLE Store
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    address  VARCHAR(255) NOT NULL,
    location POINT        NOT NULL SRID 4326
);

CREATE TABLE Stock
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    store_id   BIGINT               NOT NULL,
    product_id BIGINT               NOT NULL,
    quantity   INT                  NOT NULL,
    available  BOOLEAN DEFAULT TRUE NOT NULL,
    FOREIGN KEY (store_id) REFERENCES Store (id) ON DELETE CASCADE
);

CREATE TABLE Stock_Transactions_History
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    store_id         BIGINT                              NOT NULL,
    product_id       BIGINT                              NOT NULL,
    old_quantity     INT                                 NOT NULL,
    new_quantity     INT                                 NOT NULL,
    consumer_email   VARCHAR(255) NULL,
    transaction_type ENUM('ADD', 'CONSUME') NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (store_id) REFERENCES Store (id) ON DELETE CASCADE
);
