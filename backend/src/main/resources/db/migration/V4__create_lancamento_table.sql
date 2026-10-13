CREATE TABLE lancamento (
                            id BIGSERIAL PRIMARY KEY,
                            descricao VARCHAR(150) NOT NULL,
                            valor NUMERIC(15,2) NOT NULL,
                            data DATE NOT NULL,
                            tipo VARCHAR(20) NOT NULL,
                            conta_id BIGINT NOT NULL REFERENCES conta(id) ON DELETE CASCADE,
                            categoria_id BIGINT NOT NULL REFERENCES categoria(id) ON DELETE RESTRICT,
                            usuario_id BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_lancamento_usuario_data ON lancamento (usuario_id, data);