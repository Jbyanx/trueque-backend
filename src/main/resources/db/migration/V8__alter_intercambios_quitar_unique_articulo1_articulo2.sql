-- Quitar restricciones UNIQUE de id_articulo_uno y id_articulo_dos
ALTER TABLE intercambios DROP CONSTRAINT IF EXISTS intercambios_id_articulo_uno_key;
ALTER TABLE intercambios DROP CONSTRAINT IF EXISTS intercambios_id_articulo_dos_key;
