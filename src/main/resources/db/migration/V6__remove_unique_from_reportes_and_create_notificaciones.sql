
ALTER TABLE reportes DROP CONSTRAINT IF EXISTS fk_reporte_usuario;

CREATE TABLE notificaciones (
                                id BIGSERIAL PRIMARY KEY,
                                mensaje VARCHAR(255),
                                leida BOOLEAN DEFAULT FALSE,
                                fecha_de_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                id_usuario BIGINT,
                                CONSTRAINT fk_notificacion_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);
