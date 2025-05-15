package com.ingsoft.trueque.dto.request;

import com.ingsoft.trueque.model.util.EstadoArticulo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveArticulo{
        private Long idPropietario;
        @NotBlank
        private String nombre;
        private String descripcion;
        @NotNull
        private Long idCategoria;
}
