package com.ingsoft.trueque.specification;

import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Log4j2
@Component
public class ArticuloSpecification {

    public static Specification<Articulo> conCategoria(Long idCategoria) {
        return (root, query, cb) -> {
            if (idCategoria == null) return cb.conjunction();
            return cb.equal(root.get("categoria").get("id"), idCategoria);
        };
    }

    public static Specification<Articulo> conEstado(EstadoArticulo estadoEnum) {
        return (root, query, cb) -> {
            if (estadoEnum == null) return cb.conjunction();
            return cb.equal(root.get("estado"), estadoEnum);
        };
    }

    public static Specification<Articulo> conNombre(String nombre) {
        return (root, query, cb) -> {
            if (nombre == null || nombre.isBlank()) return cb.conjunction();
            return cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
        };
    }

    public static Specification<Articulo> sinPropietario(Boolean excluirPropietario, Long idPropietario) {
        if (Boolean.FALSE.equals(excluirPropietario) || idPropietario == null) {
            return Specification.where(null);
        }

        return (root, query, cb) ->
                cb.notEqual(root.get("propietario").get("id"), idPropietario);
    }
}
