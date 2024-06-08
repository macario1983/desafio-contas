CREATE TABLE accounts_payable (
    id UUID PRIMARY KEY,
    due_date DATE NOT NULL CHECK (due_date >= CURRENT_DATE),
    payment_date DATE,
    amount NUMERIC NOT NULL CHECK (amount > 0),
    description VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL
);

COMMENT ON TABLE accounts_payable IS 'Tabela de contas a pagar';
COMMENT ON COLUMN accounts_payable.id IS 'Identificador único da conta a pagar';
COMMENT ON COLUMN accounts_payable.due_date IS 'Data de vencimento da conta';
COMMENT ON COLUMN accounts_payable.payment_date IS 'Data de pagamento da conta';
COMMENT ON COLUMN accounts_payable.amount IS 'Valor monetário da conta';
COMMENT ON COLUMN accounts_payable.description IS 'Descrição da conta';
COMMENT ON COLUMN accounts_payable.status IS 'Situação da conta';