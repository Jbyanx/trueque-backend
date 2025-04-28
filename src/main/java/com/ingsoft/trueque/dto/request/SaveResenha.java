package com.ingsoft.trueque.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveResenha {
        @Min(1)
        @Max(5)
        private Integer puntuacion;
        @NotBlank
        private String descripcion;
        @NotNull @Positive
        private Long idAutor;
        @NotNull @Positive
        private Long idIntercambio;
}
