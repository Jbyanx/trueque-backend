package com.ingsoft.trueque.specification;

import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

@Log4j2
public class ArticuloSpecification {

    public static Specification<Articulo> conCategoria(String categoria) {
        return (root, query, cb) -> {
            if (categoria == null) return cb.conjunction();
            return cb.like(cb.lower(root.get("categoria").get("nombre")), "%"+categoria.toLowerCase()+"%");
        };
    }
    public static Specification<Articulo> conEstado(String estado) {
        return (root, query, cb) -> {
            if (estado == null || estado.isBlank()) return cb.conjunction();
            try {
                EstadoArticulo estadoEnum = EstadoArticulo.valueOf(estado.toUpperCase());
                return cb.equal(root.get("estado"), estadoEnum);
            } catch (IllegalArgumentException e) {
                log.error("estado no valido, se procede a ignorar el predicado");
                return cb.disjunction(); // No matchea nada
            }
        };
    }


    public static Specification<Articulo> conNombre(String nombre) {
        return (root, query, cb) -> {
            if (nombre == null || nombre.isBlank()) return cb.conjunction();
            return cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
        };
    }

}
