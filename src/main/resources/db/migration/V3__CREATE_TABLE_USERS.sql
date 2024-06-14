CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

COMMENT ON COLUMN users.id IS 'Identificador único do usuário';
COMMENT ON COLUMN users.username IS 'Nome de usuário';
COMMENT ON COLUMN users.password IS 'Senha do usuário';