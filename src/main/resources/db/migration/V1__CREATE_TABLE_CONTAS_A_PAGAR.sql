CREATE TABLE IF NOT EXISTS contas_a_pagar (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor NUMERIC(15, 2) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    situacao VARCHAR(50) NOT NULL
);