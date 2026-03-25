CREATE TABLE customer
(
    id VARCHAR(40) PRIMARY KEY DEFAULT gen_random_UUID(),
    name  VARCHAR(255) NOT NULL,
    tax_id VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    cellphone VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE payment
(
    id VARCHAR(255) PRIMARY KEY DEFAULT gen_random_uuid(),
    amount INTEGER NOT NULL,
    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status VARCHAR(100) NOT NULL,
    external_id VARCHAR(255),
    pix_key VARCHAR(255),
    customer_id VARCHAR (40),

    CONSTRAINT fk_payment FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);