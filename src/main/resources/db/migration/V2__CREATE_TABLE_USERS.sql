CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

COMMENT ON COLUMN users.id IS 'Identificador único do usuário';
COMMENT ON COLUMN users.username IS 'Nome de usuário';
COMMENT ON COLUMN users.password IS 'Senha do usuário';
COMMENT ON COLUMN users.account_non_expired IS 'Indica se a conta não está expirada';
COMMENT ON COLUMN users.account_non_locked IS 'Indica se a conta não está bloqueada';
COMMENT ON COLUMN users.credentials_non_expired IS 'Indica se as credenciais não estão expiradas';
COMMENT ON COLUMN users.enabled IS 'Indica se a conta está habilitada';
