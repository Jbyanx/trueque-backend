alter table personas
add column rol varchar(255) not null default 'USUARIO';

ALTER TABLE personas
    ADD CONSTRAINT uk_personas_correo UNIQUE (correo);
