CREATE TABLE meta (
                      id BIGSERIAL PRIMARY KEY,
                      nome VARCHAR(150) NOT NULL,
                      valor_alvo NUMERIC(15,2) NOT NULL,
                      valor_atual NUMERIC(15,2) NOT NULL DEFAULT 0,
                      prazo DATE NOT NULL,
                      usuario_id BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
                      created_at TIMESTAMP NOT NULL,
                      updated_at TIMESTAMP NOT NULL
);