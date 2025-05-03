ALTER TABLE resenhas RENAME COLUMN id_autor TO id_usuario_calificante;

ALTER TABLE resenhas ADD COLUMN id_usuario_calificado BIGINT;

ALTER TABLE resenhas
    ADD CONSTRAINT FK_resenha_usuario_calificado
        FOREIGN KEY (id_usuario_calificado)
            REFERENCES usuarios(id);
