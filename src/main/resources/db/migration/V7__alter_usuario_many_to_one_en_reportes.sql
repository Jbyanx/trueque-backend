-- V7: Remover restricción UNIQUE del campo id_usuario en la tabla reportes
-- Esto permitirá que un mismo usuario tenga varios reportes

-- Primero eliminamos la restricción UNIQUE
ALTER TABLE reportes DROP CONSTRAINT IF EXISTS fk_reporte_usuario;
ALTER TABLE reportes DROP CONSTRAINT IF EXISTS reportes_id_usuario_key;

-- Luego volvemos a agregar la relación como FOREIGN KEY, pero sin UNIQUE
ALTER TABLE reportes
    ADD CONSTRAINT fk_reporte_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id);
