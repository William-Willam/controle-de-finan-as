CREATE TABLE categoria (
                           id BIGSERIAL PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           tipo VARCHAR(20) NOT NULL,
                           usuario_id BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL,
                           CONSTRAINT uk_categoria_nome_usuario_tipo UNIQUE (nome, usuario_id, tipo)
);