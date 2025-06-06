package com.ingsoft.trueque.model;

import com.ingsoft.trueque.model.util.EstadoReporte;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EstadoReporte estado;

    @ManyToOne
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;

    @Column(name = "fecha_reporte", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaReporte;

}
