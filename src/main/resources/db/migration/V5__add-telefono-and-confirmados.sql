-- Agregar el campo 'telefono' a la tabla 'personas'
ALTER TABLE personas
    ADD COLUMN telefono VARCHAR(20) NOT NULL DEFAULT 'SIN_TELEFONO';

-- Agregar campos para confirmar entrega en 'intercambios'
ALTER TABLE intercambios
    ADD COLUMN confirmado_por_usuario_uno BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN confirmado_por_usuario_dos BOOLEAN NOT NULL DEFAULT FALSE;
