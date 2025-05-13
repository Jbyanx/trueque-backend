package com.ingsoft.trueque.dto.response;

import java.io.Serializable;
import java.util.List;

public record GetPerfilUsuario(
        String nombreCompleto,
        List<GetIntercambioSimple> intercambiosRealizados
) implements Serializable {
}
