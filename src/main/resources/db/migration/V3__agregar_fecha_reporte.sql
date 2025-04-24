alter table if exists reportes
    add column fecha_reporte timestamp not null default current_timestamp;
