CREATE TABLE IF NOT EXISTS bank
(
    bank_id UUID NOT NULL,
    PRIMARY KEY (bank_id)
);
CREATE TABLE IF NOT EXISTS client
(
    client_id       UUID NOT NULL,
    fio             VARCHAR(60),
    phone_number    INTEGER,
    email           VARCHAR(50),
    passport_number INTEGER,
    bank_id         UUID,
    PRIMARY KEY (client_id),
    FOREIGN KEY (bank_id) REFERENCES bank (bank_id)
);
CREATE TABLE IF NOT EXISTS credit
(
    credit_id     UUID NOT NULL,
    interest_rate DOUBLE,
    limit         DOUBLE,
    bank_id       UUID NOT NULL,
    PRIMARY KEY (credit_id),
    FOREIGN KEY (bank_id) REFERENCES bank (bank_id)
);

CREATE TABLE IF NOT EXISTS credit_offer
(
    credit_id  UUID NOT NULL,
    client_id  UUID NOT NULL,
    loan_amount DOUBLE,
    PRIMARY KEY (credit_id, client_id),
    FOREIGN KEY (credit_id) REFERENCES credit (credit_id),
    FOREIGN KEY (client_id) REFERENCES client (client_id)
);

CREATE TABLE IF NOT EXISTS payment_date
(
    id                    UUID NOT NULL,
    date                  DATE,
    amount                DOUBLE,
    body_repay_amount     DOUBLE ,
    interest_repay_amount DOUBLE,
    credit_id             UUID NOT NULL,
    client_id             UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (credit_id) REFERENCES credit (credit_id),
    FOREIGN KEY (client_id) REFERENCES client (client_id)
);

--insert into bank(bank_id) values('2fd31381-9c67-48d9-a3cb-8c80a7936f27');