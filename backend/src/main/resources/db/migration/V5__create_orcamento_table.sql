CREATE TABLE orcamento (
                           id BIGSERIAL PRIMARY KEY,
                           mes_referencia DATE NOT NULL,
                           valor_limite NUMERIC(15,2) NOT NULL,
                           categoria_id BIGINT NOT NULL REFERENCES categoria(id) ON DELETE CASCADE,
                           usuario_id BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL,
                           CONSTRAINT uk_orcamento_categoria_mes UNIQUE (categoria_id, mes_referencia)
);