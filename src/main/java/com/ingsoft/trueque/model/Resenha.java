package com.ingsoft.trueque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "resenhas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resenha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(1)
    @Max(5)
    private Integer puntuacion;
    @NotBlank
    String descripcion;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Usuario.class, fetch = FetchType.EAGER)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "intercambio_id", nullable = false)
    private Intercambio intercambio;
}
