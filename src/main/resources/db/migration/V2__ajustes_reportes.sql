-- Eliminar tabla intermedia innecesaria
drop table if exists administradores_reportes_activos;

-- Agregar columna de referencia en reportes hacia articulo
alter table if exists reportes
    add column articulo_id bigint;

-- Establecer la foreign key
alter table if exists reportes
    add constraint fk_reportes_articulo
        foreign key (articulo_id)
            references articulos(id);
