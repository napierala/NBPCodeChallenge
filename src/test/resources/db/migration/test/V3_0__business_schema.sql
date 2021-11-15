CREATE TABLE bank_account (
  id IDENTITY NOT NULL,
  user_code VARCHAR(255) NOT NULL,
  account_number VARCHAR(255) NOT NULL,
  balance_in_cents BIGINT NOT NULL,
  currency_code VARCHAR(255) NOT NULL,
  CONSTRAINT bank_account_primary_key PRIMARY KEY (id),
  CONSTRAINT bank_account_account_number_key UNIQUE KEY (account_number)
);