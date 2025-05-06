package com.ingsoft.trueque.model.util;

import java.util.Set;

import static com.ingsoft.trueque.model.util.Permiso.*;

public enum Rol {
    ADMINISTRADOR(
            Set.of(
                    LEER_ARTICULOS,
                    LEER_UN_ARTICULO,
                    ACTUALIZAR_UN_ARTICULO,
                    DESACTIVAR_UN_ARTICULO,
                    ELIMINAR_UN_ARTICULO,

                    /*CATEGORIAS*/
                    LEER_CATEGORIAS,
                    LEER_UNA_CATEGORIA,
                    ACTUALIZAR_UNA_CATEGORIA,
                    DESACTIVAR_UNA_CATEGORIA,
                    ELIMINAR_UNA_CATEGORIA,

                    /*INTERCAMBIOS*/
                    LEER_INTERCAMBIOS,
                    LEER_UN_INTERCAMBIO,
                    ACTUALIZAR_UN_INTERCAMBIO,
                    DESACTIVAR_UN_INTERCAMBIO,
                    ELIMINAR_UN_INTERCAMBIO,

                    /*REPORTES*/
                    LEER_REPORTES,
                    LEER_UN_REPORTE,
                    ACTUALIZAR_UN_REPORTE,
                    DESACTIVAR_UN_REPORTE,
                    ELIMINAR_UN_REPORTE,

                    /*RESENHAS*/
                    LEER_RESENHAS,
                    LEER_UNA_RESENHA,
                    ACTUALIZAR_UNA_RESENHA,
                    DESACTIVAR_UNA_RESENHA,
                    ELIMINAR_UNA_RESENHA,

                    VER_MI_PERFIL,
                    VER_REPUTACION
            )
    ),
    USUARIO(
            Set.of(
                    CREAR_UN_ARTICULO,
                    SOLICITAR_UN_INTERCAMBIO,
                    ACEPTAR_UN_INTERCAMBIO,
                    RECHAZAR_UN_INTERCAMBIO,
                    CANCELAR_UN_INTERCAMBIO,
                    CREAR_UN_REPORTE,
                    CREAR_UNA_RESENHA,
                    VER_REPUTACION,
                    VER_MI_PERFIL
            )
    );

    Set<Permiso> permisos;

    Rol(Set<Permiso> permissions) {
        this.permisos = permissions;
    }

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }
}
