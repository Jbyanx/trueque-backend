package com.ingsoft.trueque.model;

import com.ingsoft.trueque.model.util.EstadoReporte;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "reportes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String descripcion;

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EstadoReporte estado;
}
