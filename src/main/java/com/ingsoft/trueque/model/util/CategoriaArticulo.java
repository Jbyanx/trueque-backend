package com.ingsoft.trueque.model.util;

public enum CategoriaArticulo {
    ROPA("Ropa, Batas, prendas de vestir, ..."),
    LIBROS("Libros, Modulos, Guias, ...."),
    UTILES("Utiles, Calculadoras, Reglas, ..."),
    ACCESORIOS("Accesorios, Audifonos, Mouse, ..."),;

    String descipcion;

    CategoriaArticulo(String descipcion) {
        this.descipcion = descipcion;
    }
}
