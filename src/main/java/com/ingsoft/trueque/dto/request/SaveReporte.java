package com.ingsoft.trueque.dto.request;

import com.ingsoft.trueque.model.util.EstadoReporte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveReporte {
        @NotBlank
        private String descripcion;
        @NotNull @Positive
        private Long idAutor;
}
