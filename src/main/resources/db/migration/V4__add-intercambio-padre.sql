ALTER TABLE intercambios
    ADD COLUMN id_padre BIGINT;

ALTER TABLE intercambios
    ADD CONSTRAINT fk_intercambio_padre
        FOREIGN KEY (id_padre)
            REFERENCES intercambios(id)
            ON DELETE SET NULL;
